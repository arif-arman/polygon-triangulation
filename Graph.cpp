#include<iostream>
#include<cstdio>
#include<cstdlib>
#include<cmath>
#include<fstream>
using namespace std;

#include<GL/glut.h>

#define BLACK 0, 0, 0

int V, Pv;
double *Px = 0, *Py = 0;
double *X1 = 0, *Y1 = 0, *X2 = 0, *Y2 = 0;
double scaleX = 0, scaleY = 0;

void display(){

	//clear the display
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glClearColor(BLACK, 0);	//color black
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	/********************
	/ set-up camera here
	********************/
	//load the correct matrix -- MODEL-VIEW matrix
	glMatrixMode(GL_MODELVIEW);

	//initialize the matrix
	glLoadIdentity();

	//now give three info
	//1. where is the camera (viewer)?
	//2. where is the camera is looking?
	//3. Which direction is the camera's UP direction?

	gluLookAt(0, -1, 200, 0, 0, 0, 0, 0, 1);


	//again select MODEL-VIEW
	glMatrixMode(GL_MODELVIEW);


	/****************************
	/ Add your objects from here
	****************************/
	//add objects

	//a simple rectangles

	/*	//FORGET THE FIELD
	//an square field
	glColor3f(0.4, 1, 0.4);
	glBegin(GL_QUADS);{
	glVertex3f(-100,-100,0);
	glVertex3f(100,-100,0);
	glVertex3f(100,100,0);
	glVertex3f(-100,100,0);
	}glEnd();
	*/

	//some gridlines along the field
	int i;
	// draw points
	glColor3f(0, 1, 0);
	for (int i = 0; i < Pv; i++)
	{
		glBegin(GL_POINTS); {
			glVertex3f(Px[i], Py[i], 0.1);
		} glEnd();
	}
	// draw edges
	glColor3f(1, 0, 0);
	for (int i = 0; i < Pv; i++)
	{
		int j = (i + 1) % Pv;
		glBegin(GL_LINES); {
			glVertex3f(Px[i], Py[i], 0.1);
			glVertex3f(Px[j], Py[j], 0.1);
		} glEnd();
	}
	// draw diagonals
	glColor3f(1, 1, 1);
	for (int i = 0; i < V; i++)
	{
		glBegin(GL_LINES); {
			glVertex3f(X1[i], Y1[i], 0.1);
			glVertex3f(X2[i], Y2[i], 0.1);
		} glEnd();
	}

	glColor3f(0.5, 0.5, 0.5);	//grey
	glBegin(GL_LINES); {
		for (i = -10; i <= 10; i++){

			if (i == 0)
				continue;	//SKIP the MAIN axes

			//lines parallel to Y-axis
			glVertex3f(i * 10, -100, 0);
			glVertex3f(i * 10, 100, 0);

			//lines parallel to X-axis
			glVertex3f(-100, i * 10, 0);
			glVertex3f(100, i * 10, 0);
		}
	}glEnd();

	// draw the two AXES
	glColor3f(0.5, 0.5, 0.5);	//100% white
	glBegin(GL_LINES); {
		//Y axis
		glVertex3f(0, -150, 0);	// intentionally extended to -150 to 150, no big deal
		glVertex3f(0, 150, 0);

		//X axis
		glVertex3f(-150, 0, 0);
		glVertex3f(150, 0, 0);
	}glEnd();




	//ADD this line in the end --- if you use double buffer (i.e. GL_DOUBLE)
	glutSwapBuffers();
}

void animate(){
	//codes for any changes in Models, Camera
}

void init(){

	glEnable(GL_POINT_SMOOTH);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glPointSize(6.0);

	//codes for initialization

	//clear the screen
	glClearColor(BLACK, 0);

	/************************
	/ set-up projection here
	************************/
	//load the PROJECTION matrix
	glMatrixMode(GL_PROJECTION);

	//initialize the matrix
	glLoadIdentity();

	//give PERSPECTIVE parameters
	gluPerspective(70, 1, 0.1, 10000.0);
	//field of view in the Y (vertically)
	//aspect ratio that determines the field of view in the X direction (horizontally)
	//near distance
	//far distance
}

int main(int argc, char **argv){
	ifstream myf("input.txt");
	int iter = 0, index = 0;
	if (myf.is_open()) {
		do
		{
			if (!iter++) {
				myf >> Pv;
				Px = new double[Pv];
				Py = new double[Pv];
			}
			else {
				myf >> Px[index];
				myf >> Py[index++];
			}
		} while (myf.peek() != EOF);
	}
	myf.close();


	ifstream myf1("output.txt");
	iter = 0, index = 0;
	if (myf1.is_open()) {
		do
		{
			if (!iter++) {
				myf1 >> V;
				X1 = new double[V];
				Y1 = new double[V];
				X2 = new double[V];
				Y2 = new double[V];
			}
			else {
				myf1 >> X1[index];
				myf1 >> Y1[index];
				myf1 >> X2[index];
				myf1 >> Y2[index++];
			}
		} while (myf1.peek() != EOF);
	}
	myf1.close();

	cout << V << endl;

	double maxX = 0;
	for (int i = 0; i < Pv; i++) {
		double x = Px[i];
		if (x < 0) x *= -1;
		if (x > maxX) maxX = x;
	}
	scaleX = 100 / maxX;

	double maxY = 0;
	for (int i = 0; i < Pv; i++) {
		double y = Py[i];
		if (y < 0) y *= -1;
		if (y > maxY) maxY = y;
	}
	scaleY = 100 / maxY;
	cout << scaleX << scaleY;
	for (int i = 0; i < Pv; i++) {
		Px[i] *= scaleX;
		Py[i] *= scaleY;
	}

	for (int i = 0; i < V; i++) {
		X1[i] *= scaleX;
		Y1[i] *= scaleY; 
		X2[i] *= scaleX;
		Y2[i] *= scaleY;
	}

	glutInit(&argc, argv);
	glutInitWindowSize(500, 500);
	glutInitWindowPosition(0, 0);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGB);	//Depth, Double buffer, RGB color

	glutCreateWindow("My OpenGL Program");

	init();

	glEnable(GL_DEPTH_TEST);	//enable Depth Testing

	glutDisplayFunc(display);	//display callback function
	glutIdleFunc(animate);		//what you want to do in the idle time (when no drawing is occuring)

	glutMainLoop();		//The main loop of OpenGL

	return 0;
}
