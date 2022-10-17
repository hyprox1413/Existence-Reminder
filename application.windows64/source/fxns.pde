void setupMonth(int y, int m) {
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

void renderMonth() {
  colorMode(HSB, 360);
  background(360);
  for (int i = 0; i < month.size(); i ++) {
    month.get(i).render();
  }
}

void renderDay(){
  background(255);
  fill(0);
  dateViewed.read();
}
