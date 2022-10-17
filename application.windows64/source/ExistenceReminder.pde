import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Calendar;

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

void setup() {
  fullScreen();
  background(255);
  setupMonth(curYear, curMonth);
}

void draw() {
  if (state.equals("calendarView")) {
    renderMonth();
  } else if (state.equals("dayView")) {
    renderDay();
  }
}

void mousePressed() {
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

void keyPressed() {
  if (key == ESC) {
    key = 0;
    if (state.equals("dayView")) {
      state = "calendarView";
    }
  }
}
