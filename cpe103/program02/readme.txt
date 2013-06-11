Garrett Milster
CSC 103
Program 2

Ordered lists: As expected, when the lists were already ordered, both types of insertion sorts blew right through them in less than half a second.

Reverse Order lists: For the small lists, both types of insertion sorts went through quick as expected, however the expected n^2 inefficiency of the algorithm became obvious as it took up to 14 seconds on lists of 100,000 for both types, and up to a minute for both types. Reverse order is the worst case scenario and the binary search insertion sort went a little faster on the big list which is to be expected.

Random Order: This test clearly showed that while both types of the sort are inefficient, the binary search insertion sort handles the bigger lists much better in the average cases.

Side Note:
I originally tried lists of 1000, 10000, and 100000 and they went through so fast that I decided to go up to 10000, 100000, and even 1000000. The sorts were so inefficient with the lists of 1000000 that it was already 30 minutes in and it had finished so I had to cancel the task. This really showed me how bad an n^2 algorithm can be. I expected it to take a while but not nearly that long.