/*
 *  Garrett Milster
 *  Assignment 3a
 *  CPE 471, Computer Graphics
 *
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
#include <string.h>
#include <math.h>
#define PI 3.14159265

void display(void);
void normalize(float[3]);
void normCrossProd(float[3], float[3], float[3]);

// initial viewer position
static GLdouble modelTrans[] = {0.0, 0.0, 0.0};
// initial model angle
static GLfloat theta[] = {0.0, 0.0, 0.0};
static float thetaIncr = 5.0;

//lists for the points, indices and normal
static int* list;
static GLfloat* verts, *normal; 
static float tes;

// animation transform variables
static GLdouble translate[3] = {-10.0, 0.0, 0.0};

//---------------------------------------------------------
//   Set up the view

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

//--------------------------------------------------------
//  Set up the objects
void drawSphere() {

   // this models the keyboard
   // use individual rectangular polygons to model it
   
   int i, j, k;
   

   // save the transformation state
   glPushMatrix();

   // set the material
   GLfloat diffuseColor[] = {0.8, 1.0, 0.8, 1.0};
   glMaterialfv(GL_FRONT, GL_DIFFUSE, diffuseColor);

   // locate it in the scene
   glMatrixMode(GL_MODELVIEW);
   // Adjust the translate and scale to make the monitor the right
   //    shape and in the right place
   //glTranslatef(0, -3, 4);  

   // vertices for keyboard - must outline each face



   // now load the vertices into OpenGL Quads
   glBegin(GL_QUADS);

      for (i=0, j = 0; j <(tes * tes) - tes;j++,i+=4) {
         // each quad consists of 12 numbers, in this order:
         //   the x,y,z of the first vertex, x,y,z of the second,
         //   and so on        

         // four points per quad
         
         //normal and verts pull from the list of indices to get the squares needed 
         glNormal3fv(&normal[list[i] * 3]);
         glVertex3fv(&verts[list[i]* 3]);
         glNormal3fv(&normal[list[i+1]* 3]);
         glVertex3fv(&verts[list[i+1]* 3]);
         glNormal3fv(&normal[list[i+2]* 3]);
         glVertex3fv(&verts[list[i+2]* 3]);
         glNormal3fv(&normal[list[i+3]* 3]);
         glVertex3fv(&verts[list[i+3]* 3]);

      }
   glEnd();
      
   // recover the transform state
   glPopMatrix();

   return;
}
void buildSphere()
{
   float y = -1;
   float radius = 0;
   float angle = 0;
   float increment = 360/tes; //increment by which to increase theta
   verts = (GLfloat*) malloc(sizeof(GLfloat) * 3 * tes * tes);
   normal = (GLfloat*) malloc(sizeof(GLfloat) * 3 * tes * tes);
   float addition = float(2)/(tes-1); //increases Y by constant amount depending on tesselation

   int k = 0;
   
   for(int i = 0; i < tes; i++)
   {
      radius = sqrt(1 - (y*y));
      if(i+1 == tes)
      {
         radius = 0.0; //for some reason i was getting a non zero value when my 'y' equaled 1, I tried several print tests but nothing explained it.
      }   
      for(int j = 0; j < tes; j++)
      {
         angle = j*increment;
         
         verts[k] = radius * sin(angle*PI/180);
         verts[k+1] = y;
         verts[k+2] = radius * cos(angle*PI/180);
         
         k+= 3;      
      }
      y+= addition;
   }   

   // copies vertices into normal for later use in the draw
   memcpy(normal,verts, sizeof(GLfloat) * 3 * tes * tes);
   k = 0;

   //mallocs the list of indices
   list = (int*) malloc(sizeof(int) * (tes) * (tes) * 4);

   int a = 0;
   int b = 1;
   int c = tes+1;
   int d = tes;
   k = 0;

   // loops through and calculates the indices of each square for glquad

   for(int i = 0; i < tes - 1; i++)
   {
      for(int j = 0; j < tes; j++)  
      {
         printf("ROW %d - a: %d, b: %d, c: %d, d: %d\n",i, a,b,c,d);
         list[k] = a++;
         list[k+1] = b++;
         list[k+2] = c++;
         list[k+3] = d++;
          k+=4;

      }
   }
   k=0;
   // for(int i = 0; i <  ((tes) * (tes) * 4) - (tes*4); i+=4)
   // {
   //    printf("ROW %d: %d,%d,%d,%d \n", i, list[k], list[k+1], list[k+2], list[k+3]);
   //    k+=4;
   // }
          

}

//function called when the sphere is ready to be drawn
void displaySphere (void) {
   // this code executes whenever the window is redrawn (when opened,
   //   moved, resized, etc.
   glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

   // set the viewing transform
   setUpView();

   // set up light source
   setUpLight();

   // start drawing objects
   setUpModelTransform();
   drawSphere();

   glutSwapBuffers();
}

void promptUser()
{
   
   printf("Please enter the desired tesselation: ");
   scanf(" %f", &tes);
   
   buildSphere();
   displaySphere();
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
      displaySphere();
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
   if (key == 'q') 
   {
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
   displaySphere();
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

