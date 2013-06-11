
/*
 *  Garrett Milster
 *  Assignment 2
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
//#include "GLSL_helper.h"
#include <math.h>
#define PI 3.14159265
// some function prototypes
void display(void);
void normalize(float[3]);
void normCrossProd(float[3], float[3], float[3]);

// initial viewer position
static GLdouble modelTrans[] = {0.0, 0.0, -5.0};
// initial model angle
static GLfloat theta[] = {0.0, 0.0, 0.0};
static float thetaIncr = 5.0;
static GLfloat current[] = {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1};
static GLfloat identity[] = {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1};
static GLfloat tempMatrix[] = {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1};
static GLfloat matrix[] = {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1};
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
   // a directional light source to serve as a headlight
   GLfloat lightDir[] = {0.0, 0.0, 5.0, 0.0};
   GLfloat diffuseComp[] = {1.0, 1.0, 1.0, 1.0};

   glEnable(GL_LIGHTING);
   glEnable(GL_LIGHT0);

   glLightfv(GL_LIGHT0, GL_POSITION, lightDir);
   glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuseComp);

   return;
}




void reshapeCallback(int w, int h) {
   // from Angel, p.562

   glViewport(0,0,w,h);

   glMatrixMode(GL_PROJECTION);
   glLoadIdentity();
   if (w < h) {
      glFrustum(-2.0, 2.0, -2.0*(GLfloat) h / (GLfloat) w,
                2.0*(GLfloat) h / (GLfloat) w, 2.0, 20.0);
   }
   else {
      glFrustum(-2.0, 2.0, -2.0*(GLfloat) w / (GLfloat) h,
                2.0*(GLfloat) w / (GLfloat) h, 2.0, 20.0);
   }

   glMatrixMode(GL_MODELVIEW);
}
void printM(GLfloat a[])
{
   for(int i = 0; i < 16; i += 4)
   {
      printf("%f ", a[i]);
      printf("%f ", a[i + 1]);
      printf("%f ", a[i + 2]);
      printf("%f ", a[i + 3]);
      printf("\n");
   }
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

// Multiplies two 4x4 matrices together
void multiplyMatrix()
{
   int a = 0;
   int b = 4;
   int c = 8;
   int d = 12;
   float sum = 0;

   // copies current matrix over to temp so math can be done
   GLfloat fcurrent[16];
   for(int j = 0; j < 16; j++)
   {
      fcurrent[j] = tempMatrix[j];
   }
   for(int i = 0; i < 16; i+= 4)
   {
      for(int j = 0; j < 4; j++)
      {
         sum = (matrix[i] * fcurrent[a + j]) + (matrix[i + 1] * fcurrent[b + j]) + (matrix[i + 2] * fcurrent[c + j]) + (matrix[i + 3] * fcurrent[d+ j]);
         tempMatrix[i + j] = sum; // overwrites old matrix with new values
         sum = 0;
      }

   }   

}

// builds translation matrix
void buildTranslate(float x, float y, float z)
{
   matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1.0;
   matrix[1] = matrix[2] = matrix[3] = matrix[4] =
   matrix[6] = matrix[7] = matrix[8] = matrix[9] =
   matrix[11] = matrix[12] = matrix[13] = matrix[14] = 0.0;

   matrix[12] = x;
   matrix[13] = y;
   matrix[14] = z;

}	

// builds rotation matrix
void buildRotate(float angle, char axis)
{
   matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1.0;
   matrix[1] = matrix[2] = matrix[3] = matrix[4] =
   matrix[6] = matrix[7] = matrix[8] = matrix[9] =
   matrix[11] = matrix[12] = matrix[13] = matrix[14] = 0.0;
   
   if(axis == 'x')
   {
      matrix[5] = cos(angle*PI/180);
      matrix[6] = -1 * sin(angle*PI/180);
      matrix[9] = sin(angle*PI/180);
      matrix[10] = cos(angle*PI/180);

   }
   else if(axis == 'y')
   {
      matrix[0] = cos(angle*PI/180);
      matrix[2] = sin(angle*PI/180);
      matrix[8] = -1 * sin(angle*PI/180);
      matrix[10] = cos(angle*PI/180);
   } 
   else
   {
      matrix[0] = cos(angle*PI/180);
      matrix[1] = sin(angle*PI/180);
      matrix[4] = -1 * sin(angle*PI/180);
      matrix[5] = cos(angle*PI/180);
   }

}  

// builds scale matrix
void buildScale(float x, float y, float z)
{
   matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1.0;
   matrix[1] = matrix[2] = matrix[3] = matrix[4] =
   matrix[6] = matrix[7] = matrix[8] = matrix[9] =
   matrix[11] = matrix[12] = matrix[13] = matrix[14] = 0.0;

   matrix[0] = x;
   matrix[5] = y;
   matrix[10] = z;

}  


// prompts the user for action and reacts appropriately 
void promptUser()
{
   char transType;
   float x, y, z;
   char axis;
   float angle;

   printf("Please enter a transform command: ");
   scanf(" %c", &transType);

   if(transType == 't')
   {
      printf("Please enter the translation amounts: ");
      scanf("%f %f %f", &x, &y, &z);
      glPushMatrix();
      glTranslatef(x, y, z);
      glPopMatrix();

   }
   else if (transType == 'r')
   {
      printf("Please enter a rotation angle and axis: ");
      scanf("%f %c", &angle, &axis);
      glPushMatrix();
      if(axis == 'x')
      {
         glRotatef(angle, 1, 0, 0);
      }
      else if(axis == 'y')
      {
         glRotatef(angle, 0, 1, 0);
      }
      else
      {
         glRotatef(angle, 0, 0, 1);
      }

      glPopMatrix();


   }
   else if(transType == 's')
   {
      printf("Please enter the scale amounts: ");
      scanf("%f %f %f", &x, &y, &z);
      glPushMatrix();
      glScalef(x, y, z);
      glPopMatrix();
   }

   // if concatenate is called, incoming matrix is multiplied against current matrix
   // to store all transforms together
   if(transType == 'c')
   {
      multiplyMatrix();

   } 

   // applies transformations, copies the tempMatrix into Current 
   // which will alter the object in the display() function
   if(transType == 'a')
   {
      display();
   }   

   // resets the both the display matrix and tempMatrix then resets the object
   if(transType == 'h')
   {
      for(int i = 0; i < 16; i++)
      {
         tempMatrix[i] = identity[i];
         current[i] = identity[i];
      }
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
   if (key == 'q') // q key prompts user
   {
      promptUser();
   }
   if (key == 'p') // q key prompts user
   {
      printM(tempMatrix);
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

   //display();
}


//---------------------------------------------------------
//  Main routines

void drawBox() {
   glPushMatrix();
   
   GLfloat diffuseColor[] = {0.0, 0.0, 1.0, 1.0};
   glMaterialfv(GL_FRONT, GL_DIFFUSE, diffuseColor);
   
   glMatrixMode(GL_MODELVIEW);

   glutSolidCube(3.0);
   
   glPopMatrix();
   return;
}

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

   glMultMatrixf(current); // applies transformations to object matrix
   drawBox();

   glutSwapBuffers();
}

// create a double buffered 500x500 pixel color window
int main(int argc, char* argv[]) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE | GLUT_DEPTH);
	glutInitWindowSize(500, 500);
	glutInitWindowPosition(100, 100);
	glutCreateWindow("Hierarchical Transformations: Lab 4");
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
