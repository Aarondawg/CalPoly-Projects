void MMonDevice(float *m, float *n, float *p, int width)
{
   // allocate and load source matrices to device memoryo   
   int size = width * width  * sizeof(float);
   float *Md, Nd, Pd;

   cudaMalloc(&Md,size);
   cudaMemcpy(Md, M, size, cudaMempcyHostToDevice);

   cudaMalloc(&Nd, size);
   cudaMalloc(Nd, N, size, cudaMemcpyHostToDevice);
   
   cudaMalloc(&Pd, size);
   
   cudaMalloc(&Pd, size);


   //invoke kernel
   dim3 dimGrid(1,1);
   dim3 dimBlock(width,width);
   MMKernal(Md, Nd, Pd, width);

   //read result from device
   cudaMemcpy(P,Pd, size, cudaMemcpyDeviceToHost);
   

   //clean up
   cudaFree(Md);
   CudaFree(Nd);
   CudaFree(Pd);
}

__global__ void MMKernal(float *Md, float *Nd, float *Pd,int width)
{
   float Pvalue = 0.0;
   for(int k = 0; k < width; k++)
   {
      float Melement = Md[threadIdx.y*width + k];
      float Nelement = Nd[k*width + threadIdx.x];
      Pvalue += Melement * Nelement;
      
   }
   
   Pd[threadIdx.y*width + threadIdx.x] = Pvalue;


}
