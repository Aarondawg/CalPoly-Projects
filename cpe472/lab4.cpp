
/*
 *  Lab exercise 4.0 
 *  CPE 471, Computer Graphics
 *  Copyright 2005 Chris Buckalew
 *
/*--------------------------------------------------------------
 *  This code contains an example of a hierarchical model
 *  in OpenGL. The model is a hand with fingers. The bottom of 
 *  the palm is at the origin, and the index finger has two joints
 *  operated by key callbacks - 'a', 'b', and 'c' operate the joints
 *  (capitalize to reverse the rotations) and 'd' operates the wrist.
 *  These joints only rotate about an axis parallel to the x axis.
 *
 *  Your lab assignment is in two parts: first, add two more fingers.  
 *  You don't need to have these fingers operate - just bend the joints
 *  a little so the finger doesn't look perfectly straight.  Next,
 *  add forearm and upper arm links to the model.  Control these 
 *  joints with the 'e' and 'f' keys.  Move the shoulder joint also 
 *  relative to the z axis with the 'g' key so that the shoulder has
 *  two degrees of freedom.
 *
 *  Make the lower arm 10 units long and the upper arm 14 units long.
 *
 *------------------------------------------------------------
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

// some function prototypes
void display(void);
void normalize(float[3]);
void normCrossProd(float[3], float[3], float[3]);

// initial viewer position
static GLdouble modelTrans[] = {0.0, 0.0, -5.0};
// initial model angle
static GLfloat theta[] = {0.0, 0.0, 0.0};
static float thetaIncr = 5.0;
static float palmTheta = 0.0;
static float finger1Theta = 0.0;
static float finger2Theta = 0.0;
static float finger3Theta = 0.0;
static float finger4Theta = 0.0;
static float finger5Theta = 0.0;
static float finger6Theta = 0.0;

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
   // a directional light source to serve as a headlight
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

void drawFingerAndJoint(float xSize, float ySize, float zSize,
              float xTrans, float yTrans, float zTrans) {

   // save the transformation state
   glPushMatrix();

   // color of the joint
   GLfloat diffuseColor[] = {1, 0.1, 0.1, 1.0};
   glMaterialfv(GL_FRONT, GL_DIFFUSE, diffuseColor);

   // locate it in the scene
   glMatrixMode(GL_MODELVIEW);

   // draw the joint
   glutSolidSphere(0.5, 10,10);

   // color of the fingerLink
   GLfloat diffuseColor2[] = {0.6, 1.0, 0.6, 1.0};
   glMaterialfv(GL_FRONT, GL_DIFFUSE, diffuseColor2);
   // note that center of cube is at origin
   // translate it up 1.0 in y so that *bottom* of cube is at origin
   glScalef(xSize, ySize, zSize);  
   glTranslatef(0, 1, 0);	 

   // draw the cube - the parameter is the length of the sides
   glutSolidCube(2.0);

   // recover the transform state
   glPopMatrix();

   return;
}
void drawFullFinger()
{
      // draw first finger joint
      glPushMatrix();
         // this translation moves the the bottom of the finger to the 
         // top of the palm, which was 8 units tall.  
         glTranslatef(0, 8, 0);
         // rotate after translation of finger upward
         glRotatef(finger1Theta, 1, 0, 0);

         // finger size is 0.75x3x0.75
         drawFingerAndJoint(0.375, 1.5, 0.375, 0, 0, 0);         

         // draw second finger joint
         // move to top of first finger link
         glTranslatef(0, 3.0, 0);
         glRotatef(finger2Theta, 1, 0, 0);
         drawFingerAndJoint(0.375, 1.0, 0.375, 0, 0, 0);         

         // draw third finger joint
         glTranslatef(0, 2.0, 0);
         glRotatef(finger3Theta, 1, 0, 0);
         drawFingerAndJoint(0.375, 0.75, 0.375, 0, 0, 0);         

      glPopMatrix();
}
void drawFigure() {
   glPushMatrix();
         
         glRotatef(finger6Theta, 0, 0, 1);
         glRotatef(finger5Theta, 1, 0, 0);
         drawFingerAndJoint(3.0, 7, 0.5, 0, 0, 0);  
         glTranslatef(0, 15, 0);
         glRotatef(finger4Theta, 1, 0, 0);
         drawFingerAndJoint(3.0, 5, 0.5, 0, 0, 0);  
                  glTranslatef(0, 10, 0);
      glRotatef(palmTheta, 1, 0, 0);
      drawFingerAndJoint(1.5, 4, 0.5, 0, 0, 0);
         drawFullFinger();
         glTranslatef(-1.5, 0, 0);
         drawFullFinger();
         glTranslatef(3, 0, 0);
         drawFullFinger();

         glTranslatef(0, -10.5, 0);
           
      glPopMatrix();    

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
                2.0*(GLfloat) h / (GLfloat) w, 2.0, 100.0);
   }
   else {
      glFrustum(-2.0, 2.0, -2.0*(GLfloat) w / (GLfloat) h,
                2.0*(GLfloat) w / (GLfloat) h, 2.0, 100.0);
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

   // model joint callbacks
   if (key == 'd') palmTheta -= 4.0;
   if (key == 'D') palmTheta += 4.0;
   if (key == 'c') finger1Theta -= 4.0;
   if (key == 'C') finger1Theta += 4.0;
   if (key == 'b') finger2Theta -= 4.0;
   if (key == 'B') finger2Theta += 4.0;
   if (key == 'a') finger3Theta -= 4.0;
   if (key == 'A') finger3Theta += 4.0;
   if (key == 'e') finger4Theta -= 4.0;
   if (key == 'E') finger4Theta += 4.0;
   if (key == 'f') finger5Theta -= 4.0;
   if (key == 'F') finger5Theta += 4.0;
   if (key == 'g') finger6Theta -= 4.0;
   if (key == 'G') finger6Theta += 4.0;
   display();
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
   drawFigure();

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

