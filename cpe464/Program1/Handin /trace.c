/* Garrett Milster 
 *
 * Trace is a program that reads packet headers
 */ 
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <pcap.h>
#include <arpa/inet.h>
#include "checksum.h"
	
int ethernet_header(const u_char* data_pointer);
void arp_header(const u_char* data_pointer, int index);
void ip_header(const u_char* data_pointer, int index);
void udp_header(const u_char *data_pointer, int index);
void icmp_header(const u_char *data_pointer, int index);

/* stuct used for tcp pseudo header */
typedef struct sudoheader{
	uint32_t dest;
	uint32_t srce;
	char zero;
	char ptcl;
   uint16_t length;	
}Header;

void tcp_header(const u_char *data_pointer, int index, Header iphdr);
	
int main(int argc, char* argv[])
{
	// making sure correct number of arguments are used when program is executed
	if(argc != 2)
	{
		printf("Usage: trace [filename]\n");
		return -1;
	}
	int packetcount = 0;
	int packetlen = 0;
	char errbuf[PCAP_ERRBUF_SIZE];
	const char *fname = argv[1];
	pcap_t *buffer = pcap_open_offline(fname, errbuf);
	struct pcap_pkthdr *next;
	const u_char *data;
	printf("\n");
	
	//this loop cycles through each packet and begins reading the ethernet header
	// this will eventually call a functin for the arp header, or the ip header, or
	// it will return back to the loop if an unknown header type is encountered.
	while(pcap_next_ex(buffer, &next, &data) == 1)
    {
		packetcount++;
		packetlen = next->len;
		printf("Packet number: %d  Packet Len: %d\n", packetcount, packetlen);
		ethernet_header(data);		
	}

	return 0;
}

int ethernet_header(const u_char* data_pointer)
{
	
	int index = 0;
	int i = 0;
	printf("\n\tEthernet Header\n");
	printf("\t\tDest MAC: ");
	
	//this loop and the one following it print out the destination
	//and source mac addresses for the ethernet header.
	for(i = index ;i < index + 6; i++)
	{
		printf("%x", data_pointer[i]);
		if(i == (index + 5))
		{
			break;
		}
		printf(":");
	}
	index = i + 1;
	printf("\n");
	printf("\t\tSource MAC: ");
	for(i = index;i < index + 6; i++)
	{
		printf("%x", data_pointer[i]);
		if(i == (index + 5))
		{
			break;
		}
		printf(":");
	}
	index = i + 2;
	printf("\n");
	
	// these conditionals check if the next header is ARP, IP, or 
	// an unsupported header format.
	if((int)data_pointer[index] == 6)
	{
		printf("\t\tType: ARP\n");
		arp_header(data_pointer, index + 2);
	}
	else if((int)data_pointer[index] == 0)
	{
		printf("\t\tType: IP\n");
      ip_header(data_pointer, index + 1);
	}
	else
	{
		printf("\t\tType: Unknown Packet Header\n");
		return -1;
	}
	
	return 0;
}

void arp_header(const u_char* data_pointer, int index)
{
	int i = 0;
	// this function essentially moves the data pointer through the header 
	// printing out the required info and moving along.
	printf("\n\tARP header\n");
	index = index + 6;
	printf("\t\tOpcode: ");
	if((int)data_pointer[index] == 1)
	{
		printf("Request\n");
	}
	else
	{
		printf("Reply\n");
	}
	index++;
	printf("\t\tSender MAC: ");
	for(i = index ;i < index + 6; i++)
	{
		printf("%x", data_pointer[i]);
		if(i == (index + 5))
		{
			break;
		}
		printf(":");
	}
	index = i + 1;
	printf("\n");
	printf("\t\tSender IP: ");
	for(i = index; i < index + 4; i++)
	{
		printf("%d", data_pointer[i]);
		if(i == (index + 3))
		{
			break;
		}
		printf(".");
	}
	printf("\n");
	printf("\t\tTarget MAC: ");
	index = index + 4;
	for(i = index ;i < index + 6; i++)
	{
		printf("%x", data_pointer[i]);
		if(i == (index + 5))
		{
			break;
		}
		printf(":");
	}
	index = i + 1;
	printf("\n\t\tTarget IP: ");
	for(i = index ;i < index + 4; i++)
	{
		printf("%d", data_pointer[i]);
		if(i == (index + 3))
		{
			break;
		}
		printf(".");
	}
	printf("\n\n\n");
	// since nothing comes after ARP, it's a void function and the program 
	// moves back to the main function to go on to the next packet
}

void ip_header(const u_char *data_pointer, int index)
{
	char temp = 0;
	int mask = 1;
	int buff = 0;
	int i = 0;
	int type = 0;
   // uint16_t length = 0;
	Header iphdr;
	// a pointer to the beginning of the ip header used for the checksum function
	u_short *sum = (u_short*)&(data_pointer[index]);
	
	// this loop pulls the 4 bit header length from the first byte of 
	// the ip header.
	for(i = 0; i < 4; i++)
	{
		buff = mask & data_pointer[index];
		temp = temp | buff;
		
		mask  = mask << 1;
	}
	temp = temp * 4;	
	
	// the rest of this function progresses through the header and prints info
	// as well as populates the pseudo header struct for the tcp checksum
	index++;
	printf("\n\tIP Header\n");
	printf("\t\tTOS: 0x%x\n", data_pointer[index]);
   // length =  data_pointer[index+1] << 8;
    //length = length | data_pointer[index+2];
	memcpy(&iphdr.length, &data_pointer[index+1], 2);

	iphdr.length = ntohs(iphdr.length);
    iphdr.length = iphdr.length - temp;
   
	index = index + 7;
	printf("\t\tTTL: %d\n", data_pointer[index]);
	index++;
	printf("\t\tProtocol: ");
	iphdr.ptcl = data_pointer[index];
	iphdr.zero = 0;
	if(data_pointer[index] == 6)
	{
		printf("TCP\n");
		type = 6;
	}
	else if(data_pointer[index] == 1)
	{
		printf("ICMP\n");
		type = 1;
	}
	else if(data_pointer[index] == 17)
	{
		printf("UDP\n");
		type = 17;
	}
	else
	{
		printf("Unknown\n");
	}
	
	printf("\t\tChecksum: ");

	index++;
	uint16_t check;
	memcpy(&check, &data_pointer[index],2);
	if(in_cksum(sum, temp) == 0)
	{
		printf("Correct (0x%x)\n", htons(check));
	}
	else
	{
		printf("Incorrect (0x%x)\n",htons(check));
	}
	index = index + 2;

	printf("\t\tSender IP: ");
	iphdr.srce = 0;
	
	// this for loop and the one following it print out the sender and destination addresses
	memcpy(&iphdr.srce, &data_pointer[index], 4);
	for(i = index; i < index + 4; i++)
	{
	//	iphdr.srce = iphdr.srce | data_pointer[i];
		printf("%d", data_pointer[i]);
		if(i == (index + 3))
		{
			break;
		}
	//	iphdr.srce = iphdr.srce << 8;
		printf(".");
	}
	index = i + 1;
	iphdr.dest = 0;
	printf("\n\t\tDest IP: ");
	memcpy(&iphdr.dest, &data_pointer[index], 4);
	
	for(i = index; i < index + 4; i++)
	{
		printf("%d", data_pointer[i]);
	//	iphdr.dest = iphdr.dest | data_pointer[i];
		
		if(i == (index + 3))
		{
			break;
		}
	//	iphdr.dest = iphdr.dest << 8;
		printf(".");
	}
	index = i + 1;
	
	//this conditional calls one of the three possible header formats to follow ip
	printf("\n\n");
	if(type == 6)
	{
		tcp_header(data_pointer, index, iphdr);
	}
	else if(type == 1)
	{
		icmp_header(data_pointer, index);
	}
	else if(type == 17)
	{
		udp_header(data_pointer, index);
	}
}

void udp_header(const u_char *data_pointer, int index)
{
	//this function prints out the source and destination ports for the UDP header
	int buff;
	printf("\tUDP Header\n");
	if((int)data_pointer[index] == 0)
	{
		printf("\t\tSource Port:  %d\n", data_pointer[index+1]);
	}
	else
	{
		buff =  data_pointer[index] << 8;
		buff = buff | data_pointer[index+1];
		printf("\t\tSource Port:  %d\n", buff);
	}
	index = index + 2;
	if((int)data_pointer[index] == 0)
	{
		printf("\t\tDest Port:  %d\n", data_pointer[index+1]);
	}
	else
	{
		buff =  data_pointer[index] << 8;
		buff = buff | data_pointer[index+1];
		printf("\t\tDest Port:  %d\n", buff);
	}
	printf("\n");
	
}

void icmp_header(const u_char *data_pointer, int index)
{
	// this is a simple function that sees if the ICMP header has a request or reply value stored
	printf("\tICMP Header\n");
	printf("\t\tType: ");
	if(data_pointer[index] == 0)
	{
		printf("Reply\n\n");
	}
	else if(data_pointer[index] == 8)
	{
		printf("Request\n\n");
	}
   else
   {
      printf("Unknown\n\n");
   }
	
}

void tcp_header(const u_char *data_pointer, int index, Header iphdr)
{
	int buff;
	const u_char *temp_pointer = data_pointer+index;

	printf("\tTCP Header\n");
	//These two conditionals print out the source and destination ports
	if((int)data_pointer[index] == 0)
	{
		printf("\t\tSource Port:  ");
	    if(data_pointer[index+1] == 80)
      	{
        	printf("HTTP\n");
      	}
      	else
      	{
         	printf("%d\n", data_pointer[index+1]);
      	}
	}
	else
	{
		buff =  data_pointer[index] << 8;
		buff = buff | data_pointer[index+1];
 		printf("\t\tSource Port:  %d\n", buff); 
	}
	index = index + 2;
	if((int)data_pointer[index] == 0)
	{
		printf("\t\tDest Port:  ");
	    if(data_pointer[index+1] == 80)
      	{
        	printf("HTTP\n");
      	}
      	else
      	{
         	printf("%d\n", data_pointer[index+1]);
      	}
	}
	else
	{
		buff =  data_pointer[index] << 8;
		buff = buff | data_pointer[index+1];
 		printf("\t\tDest Port:  %d\n", buff); 
	}
	
	//these series of bitwise math extract the sequence and acknowledgement numbers
	index = index + 2;
	buff =  data_pointer[index++] << 8;
	buff = buff | data_pointer[index++];
	buff =  buff << 8;
	buff = buff | data_pointer[index++];
	buff =  buff << 8;
	buff = buff | data_pointer[index++];
	printf("\t\tSequence Number: %u\n", buff);
	
	buff =  data_pointer[index++] << 8;
	buff = buff | data_pointer[index++];
	buff =  buff << 8;
	buff = buff | data_pointer[index++];
	buff =  buff << 8;
	buff = buff | data_pointer[index++];
	printf("\t\tACK Number: %u\n", buff);
	int fin, syn, rst;
	int mask = 1;
	index++;
	
	//this bitwise math and conditionals pull out the 
	//3 flags fin,syn, and rst and print out their values.
	fin = mask & data_pointer[index];
	mask = mask << 1;
	syn = mask & data_pointer[index];
	syn = syn >> 1;
	mask = mask << 1;
	rst = mask & data_pointer[index];
	rst = rst >> 2;
	if(syn == 1)
	{
		printf("\t\tSYN Flag: Yes\n");
	}
	else
	{
		printf("\t\tSYN Flag: No\n");
	}
	if(rst == 1)
	{
		printf("\t\tRST Flag: Yes\n");
	}
	else
	{
		printf("\t\tRST Flag: No\n");
	}
	if(fin == 1)
	{
		printf("\t\tFIN Flag: Yes\n");
	}
	else
	{
		printf("\t\tFIN Flag: No\n");
	}
	

	index++;
	buff =  data_pointer[index++] << 8;
	buff = buff | data_pointer[index++];
	printf("\t\tWindow Size: %d\n", buff);

	//this code is combining the pseudo header and tcp header/data together 
	//to calculate the checksum.
	u_char *tcp_chksum = (u_char*)malloc(12 + iphdr.length);


	u_char *temp = tcp_chksum;
	memcpy(tcp_chksum, &iphdr.dest, 4);	
	tcp_chksum = tcp_chksum + 4;
	memcpy(tcp_chksum, &iphdr.srce, 4);
	
	tcp_chksum = tcp_chksum + 4;
	memcpy(tcp_chksum, &iphdr.zero, 1);
	
	tcp_chksum++;
	memcpy(tcp_chksum, &iphdr.ptcl, 1);
	
	tcp_chksum++;
	iphdr.length = htons(iphdr.length);
	memcpy(tcp_chksum, &iphdr.length, 2);
	iphdr.length = ntohs(iphdr.length);
	
	tcp_chksum = tcp_chksum + 2;
   memcpy(tcp_chksum,temp_pointer, iphdr.length);

	buff =  data_pointer[index++] << 8;
	buff = buff | data_pointer[index++];	
	printf("\t\tChecksum: ");
	
	if(in_cksum((u_short*)temp, 12 + iphdr.length) == 0)
	{
		printf("Correct (0x%x)\n", buff);
	}
	else
	{
		printf("Incorrect (0x%x)\n", buff);
	}

	printf("\n");
	
}
