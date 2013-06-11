/*
 *  Garrett Milster
 *  Assignment 3A
 *  CPE 471, Computer Graphics
 *     
 */

#ifdef __APPLE__
#include "GLUT/glut.h"
#include <OPENGL/gl.h>
#endif
#ifdef __unix__
#include <GL/glut.h>
#endif

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#define PI 3.14159265

typedef struct point {
  GLfloat x;
  GLfloat y;
  GLfloat z;
}Point;
// some function prototypes
void display(void);
void normalize(float[3]);
void normCrossProd(float[3], float[3], float[3]);

// initial viewer position
static GLdouble modelTrans[] = {0.0, 0.0, -5.0};
// initial model angle
static GLfloat theta[] = {0.0, 0.0, 0.0};
static float thetaIncr = 5.0;
static int num;
static int flag = 0;
// animation transform variables
static GLdouble translate[3] = {-10.0, 0.0, 0.0};
GLfloat* points; 
int* list;

//---------------------------------------------------------
//   Set up the view


void printSphere(Point* pointList)
{
   for(int i = 0; i < num*num; i+= num)
   {
      printf("Row: %d --- ", i);
      for(int j = 0; j < num; j++)
      {
         printf("%f,%f ", pointList[i+j].x, pointList[i+j].z);
      }
      printf("\n");
   }

}
void drawSphere() {

   // this models the keyboard
   // use individual rectangular polygons to model it
   
   int i, j, k;
   GLfloat* normal = (GLfloat*) malloc(sizeof(GLfloat) * 3 * num * num); 
   for(int a = 0; a < 3*num*num; a++)
   {
      normal[a] = points[a];
   }

   // save the transformation state
   glPushMatrix();

   // set the material
   GLfloat diffuseColor[] = {0.8, 1.0, 0.8, 1.0};
   glMaterialfv(GL_FRONT, GL_DIFFUSE, diffuseColor);

   // locate it in the scene
   glMatrixMode(GL_MODELVIEW);

   printf("attempt\n");
   // now load the vertices into OpenGL Quads
   glBegin(GL_QUADS);
      printf("here\n");
      for (i=0, k=0; k < ((num-1) * (num-1)); k++, i+=12) {

         // each quad consists of 12 numbers, in this order:
         //   the x,y,z of the first vertex, x,y,z of the second,
         //   and so on 

         // compute the normal - the same for all four corners

      //printf("inside\n");
         printf("%d - ", i);
         // this is the normal
         glNormal3fv(&normal[list[i]]);
         glVertex3fv(&points[list[i]]);
         glNormal3fv(&normal[list[i + 3]]);
         glVertex3fv(&points[list[i + 3]]);
         glNormal3fv(&normal[list[i + 6]]);
         glVertex3fv(&points[list[i + 6]]);
         glNormal3fv(&normal[list[i + 9]]);
         glVertex3fv(&points[list[i + 9]]);
   //printf("HERE\n");


      }

   glEnd();
   free(list);
   free(points);
   free(normal);
   // recover the transform state
   glPopMatrix();
      //free(list);
     // free(normal);
   return;
}
void buildSphere()
{
   GLfloat y = -1;
   GLfloat phi = 0;
   GLfloat radius = 0;
   GLfloat thetaR = 0;
   GLfloat increment = 360/float(num);
   points = (GLfloat*) malloc(sizeof(GLfloat) * 3 * num * num);   
   GLfloat addition = float(2)/(float(num-1));
   int k = 0;
   for(int i = 0; i < num*num; i+= num)
   {
      
      radius = sqrt(1 - (y * y));//fabs(cos(phi));
      
      if(i+num == num*num)
      {
         
         radius = 0.0;
      }
      for(int j = 0; j < num; j++)
        {
            thetaR = j * increment;

             points[k++] = radius * sin(thetaR*PI/180);
             points[k++] = y;
             points[k++] = radius * cos(thetaR*PI/180);
        } 

       y = y + addition;

   }
   printf("done\n");
   int a = 0;
   int b = 1;
   int c = a + num;
   int d = b + num;
   k = 0;
   list = (int*) malloc(sizeof(int) * 3 * num * num);   

   for(int i = 0; i <((num*num) - num - 1); i+=num)
   {
      for(int j = 0; j < num; j++)
      {
         if((i+j) == ((num*num) - num - 1))
         {
            break;
         } 
         list[k] = a;
         list[k+1] = a;
         list[k+2] = a++;
          //printf("ROW %d: %f,%f,%f  --- ", j, list[k], list[k+1], list[k+2]);
          k+=3;
          list[k] = b;
          list[k+1] = b;
          list[k+2] = b++;
            //      printf(" %f,%f,%f  --- ", list[k], list[k+1], list[k+2]);
          k+=3;
          list[k] = c;
          list[k+1] = c;
          list[k+2] = c++;
              //     printf(" %f,%f,%f  --- ",  list[k], list[k+1], list[k+2]);
          k+=3;

          list[k] = d;
          list[k+1] = d;
          list[k+2] = d++;
                //   printf(" %f,%f,%f\n",  list[k], list[k+1], list[k+2]);
          k+=3;

      }
   }
   //printSphere(pointList);
    //free(pointList);
   flag = 1;
   


}

void promptUser()
{
   int tes = 0;
   flag = 0;
   printf("Please enter the desired tesselation: ");
   scanf(" %d", &tes);
   num = tes;
   buildSphere();
}


void setUpView() {
   // this code initializes the viewing transform
   glLoadIdentity();

   // moves viewer along coordinate axes
   gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

   // move the view back some relative to viewer[] position
   glTranslatef(0.0f,0.0f, -8.0f);

   // rotates view
   glRotatef(0, 1.0, 0.0, 0.0);
   glRotatef(0, 0.0, 1.0, 0.0);
   glRotatef(0, 0.0, 0.0, 1.0);

   return;
}

//----------------------------------------------------------
//  Set up model transform

void setUpModelTransform() {

   // moves model along coordinate axes
   glTranslatef(modelTrans[0], modelTrans[1], modelTrans[2]);

   // rotates model
   glRotatef(theta[0], 1.0, 0.0, 0.0);
   glRotatef(theta[1], 0.0, 1.0, 0.0);
   glRotatef(theta[2], 0.0, 0.0, 1.0);


}

//----------------------------------------------------------
//  Set up the light

void setUpLight() {
   // set up the light sources for the scene
   // a directional light source from over the right shoulder
   GLfloat lightDir[] = {0.0, 0.0, 5.0, 0.0};
   GLfloat diffuseComp[] = {1.0, 1.0, 1.0, 1.0};

   glEnable(GL_LIGHTING);
   glEnable(GL_LIGHT0);

   glLightfv(GL_LIGHT0, GL_POSITION, lightDir);
   glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuseComp);

   return;
}

//-----------------------------------------------------------
//  Callback functions

void reshapeCallback(int w, int h) {
   // from Angel, p.562

   glViewport(0,0,w,h);

   glMatrixMode(GL_PROJECTION);
   glLoadIdentity();
   if (w < h) {
      glFrustum(-2.0, 2.0, -2.0*(GLfloat) h / (GLfloat) w,
                2.0*(GLfloat) h / (GLfloat) w, 2.0, 200.0);
   }
   else {
      glFrustum(-2.0, 2.0, -2.0*(GLfloat) w / (GLfloat) h,
                2.0*(GLfloat) w / (GLfloat) h, 2.0, 200.0);
   }

   glMatrixMode(GL_MODELVIEW);
}

void mouseCallback(int button, int state, int x, int y) {
   // rotate camera
   GLint axis = 3;
   if (button == GLUT_LEFT_BUTTON && state == GLUT_DOWN) axis = 0;
   if (button == GLUT_MIDDLE_BUTTON && state == GLUT_DOWN) axis = 1;
   if (button == GLUT_RIGHT_BUTTON && state == GLUT_DOWN) axis = 2;
   if (axis < 3) {  // button ups won't change axis value from 3
      theta[axis] += thetaIncr;
      if (theta[axis] > 360.0) theta[axis] -= 360.0;
      display();
   }
}   

void keyCallback(unsigned char key, int x, int y) {
   // move viewer with x, y, and z keys
   // capital moves in + direction, lower-case - direction
   if (key == 'x') modelTrans[0] -= 1.0;
   if (key == 'X') modelTrans[0] += 1.0;
   if (key == 'y') modelTrans[1] -= 1.0;
   if (key == 'Y') modelTrans[1] += 1.0;
   if (key == 'z') modelTrans[2] -= 1.0;
   if (key == 'Z') modelTrans[2] += 1.0;
   if (key == 'q') {
       promptUser();
   }
  
   if (key == 'r') {
      theta[0] = 0.0; theta[1] = 0.0; theta[2] = 0.0;
   }
   if (key == '-') {
      thetaIncr = -thetaIncr;
   }
   if (key == '+') {
      if (thetaIncr < 0) thetaIncr = thetaIncr - 1.0;
      else               thetaIncr = thetaIncr + 1.0;
   }
  // display();
}


//---------------------------------------------------------
//  Main routines

void display (void) {
   // this code executes whenever the window is redrawn (when opened,
   //   moved, resized, etc.
   glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

   // set the viewing transform
   setUpView();

   // set up light source
   setUpLight();

   // start drawing objects
   setUpModelTransform();
   if(flag == 0)
   {
      promptUser();
   }
   
   drawSphere();
  
   

   glutSwapBuffers();
}

// create a double buffered 500x500 pixel color window
int main(int argc, char* argv[]) {
   glutInit(&argc, argv);
   glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE | GLUT_DEPTH);
   glutInitWindowSize(500, 500);
   glutInitWindowPosition(100, 100);
   glutCreateWindow("Modeling Primitives: Lab 1");
   glEnable(GL_DEPTH_TEST);
   glutDisplayFunc(display);
   glutReshapeFunc(reshapeCallback);
   glutKeyboardFunc(keyCallback);
   glutMouseFunc(mouseCallback);
   glutMainLoop();
   return 0;
}

//---------------------------------------------------------
//  Utility functions

void normalize(float v[3]) {
   // normalize v[] and return the result in v[]
   // from OpenGL Programming Guide, p. 58
   GLfloat d = sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
   if (d == 0.0) {
      printf("zero length vector");
      return;
   }
   v[0] = v[0]/d; v[1] = v[1]/d; v[2] = v[2]/d;
}

void normCrossProd(float v1[3], float v2[3], float out[3]) {
   // cross v1[] and v2[] and return the result in out[]
   // from OpenGL Programming Guide, p. 58
   out[0] = v1[1]*v2[2] - v1[2]*v2[1];
   out[1] = v1[2]*v2[0] - v1[0]*v2[2];
   out[2] = v1[0]*v2[1] - v1[1]*v2[0];
   normalize(out);
}

