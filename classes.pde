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

  void setPos(float x, float y) {
    this.x = x;
    this.y = y;
  }

  void read() {
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

  void render() {
    if (exempt) {
      colorMode(HSB, 360);
      fill(255);
    } else {
      colorMode(HSB, 360);
      fill(30 * hours, 359.0, 359.0);
    }
    rect(x - w / 2, y - h / 2, w, h);
    textAlign(CENTER, CENTER);
    fill(0);
    textSize(20);
    if(hours != 0.0)
    text(nf(hours, 0, 2), x, y - h / 50);
  }
}
