import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException; 
import java.util.Date; 
import java.util.Calendar; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ExistenceReminder extends PApplet {







FileReader reader;
Calendar cal = Calendar.getInstance();
BufferedReader bufferedReader;
int[] monthLengths = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
ArrayList<CalDay> month;
float spaceWidth = 150;
float spaceHeight = 120;
String state = "calendarView";
CalDay dateViewed;
int curMonth = cal.get(Calendar.MONTH) + 1;
int curYear = cal.get(Calendar.YEAR);
float curMonthHours = 0;

public void setup() {
  
  background(255);
  setupMonth(curYear, curMonth);
}

public void draw() {
  if (state.equals("calendarView")) {
    renderMonth();
  } else if (state.equals("dayView")) {
    renderDay();
  }
}

public void mousePressed() {
  boolean dayClicked = false;
  if (mouseButton == LEFT) {
    if (state.equals("calendarView")) {
      for (int i = 0; i < month.size(); i ++) {          
        if (mouseX > month.get(i).x - month.get(i).w / 2 && mouseX < month.get(i).x + month.get(i).w / 2 && mouseY > month.get(i).y - month.get(i).h / 2 && mouseY < month.get(i).y + month.get(i).h / 2) {
          dateViewed = month.get(i);
          dayClicked = true;
          state = "dayView";
        }
      }
    }
    if (!dayClicked) {
      if (curMonth == 12) {
        curMonth = 1;
        curYear ++;
      } else {
        curMonth ++;
      }
      setupMonth(curYear, curMonth);
    }
  }
  if (mouseButton == RIGHT){
    if (state.equals("calendarView")) {
      for (int i = 0; i < month.size(); i ++) {    
        if (mouseX > month.get(i).x - month.get(i).w / 2 && mouseX < month.get(i).x + month.get(i).w / 2 && mouseY > month.get(i).y - month.get(i).h / 2 && mouseY < month.get(i).y + month.get(i).h / 2) {
          dateViewed = month.get(i);
          dayClicked = true;
          state = "dayView";
        }
      }
    }
    if (!dayClicked) {
      if (curMonth == 1) {
        curMonth = 12;
        curYear --;
      } else {
        curMonth --;
      }
      setupMonth(curYear, curMonth);
    }
  }
}

public void keyPressed() {
  if (key == ESC) {
    key = 0;
    if (state.equals("dayView")) {
      state = "calendarView";
    }
  }
}
class CalDay {
  float x, y;
  int year, month, day;
  float w = 130;
  float h = 100;
  String input;
  float hours;
  boolean exempt = false;

  CalDay(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day; 
    try {
      try {
        reader = new FileReader(sketchPath("data\\" + year + "-" + month + "-" + day + ".txt"));
        bufferedReader = new BufferedReader(reader);
        input = new String(bufferedReader.readLine());
        hours = Float.parseFloat(input);
      } 
      catch (NumberFormatException E) {
        if (input.equals("X")) {
          exempt = true;
        }
        hours = 0;
      }
      reader.close();
    } 
    catch (IOException e) {
      //e.printStackTrace();
    }
  }

  public void setPos(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public void read() {
    try {
      println("data\\" + year + "-" + month + "-" + day + ".txt");
      reader = new FileReader(sketchPath("data\\" + year + "-" + month + "-" + day + ".txt"));
      bufferedReader = new BufferedReader(reader);
      String line;
      int newline = 1;
      while ((line = bufferedReader.readLine()) != null) {
        textSize(30);
        text(line, 100, newline * 100);
        newline ++;
      }
      reader.close();
    } 
    catch (IOException e) {
      //e.printStackTrace();
    }
  }

  public void render() {
    if (exempt) {
      colorMode(HSB, 360);
      fill(255);
    } else {
      colorMode(HSB, 360);
      fill(30 * hours, 359.0f, 359.0f);
    }
    rect(x - w / 2, y - h / 2, w, h);
    textAlign(CENTER, CENTER);
    fill(0);
    textSize(20);
    if(hours != 0.0f)
    text(nf(hours, 0, 2), x, y - h / 50);
  }
}
public void setupMonth(int y, int m) {
  month = new ArrayList<CalDay>();
  int monthLength;
  int week = 0;
  curMonthHours = 0;
  monthLength = monthLengths[m - 1];
  if (m == 2 && curYear % 4 == 0) {
    monthLength = 29;
    if (y % 100 == 0) {
      monthLength = 28;
      if (y % 400 == 0) {
        monthLength = 29;
      }
    }
  }
  Calendar c = Calendar.getInstance();
  c.set(y, m - 1, 1);
  int day = (c.get(Calendar.DAY_OF_WEEK) - 1) % 7;
  for (int i = 0; i < monthLength; i ++) {
    month.add(new CalDay(y, m, i + 1));
    month.get(i).setPos(width/2 + spaceWidth * (day - 3), height / 4 + spaceHeight * week);
    curMonthHours += month.get(i).hours;
    day ++;
    day %= 7;
    if (day == 0) {
      week ++;
    }
  }
  println(curMonthHours);
}

public void renderMonth() {
  colorMode(HSB, 360);
  background(360);
  for (int i = 0; i < month.size(); i ++) {
    month.get(i).render();
  }
}

public void renderDay(){
  background(255);
  fill(0);
  dateViewed.read();
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ExistenceReminder" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
