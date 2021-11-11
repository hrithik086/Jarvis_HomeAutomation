const int light=12;
const int fan=11;
const int computer=10;
const int pump=9;
int choice;
char fanStatus='l';
char lightStatus='l';
char computerStatus='l';
char pumpStatus='l';

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(light,OUTPUT);
  pinMode(fan,OUTPUT);
  pinMode(computer,OUTPUT);
  pinMode(pump,OUTPUT);
  digitalWrite(light,HIGH);
  digitalWrite(fan,HIGH);
  digitalWrite(computer,HIGH);
  digitalWrite(pump,HIGH);
}

void loop() {
  if(Serial.available()>0)
  {
    choice=Serial.read();
    if(choice=='2')
    {
           if(fanStatus=='l')
           {
              //digitalWrite(fan,HIGH);
              digitalWrite(fan,LOW);
              fanStatus='h';  
           }
           else
           {
              //digitalWrite(fan,LOW);
              digitalWrite(fan,HIGH);
              fanStatus='l';
           }
  }
    else if(choice=='1')
    {
          if(lightStatus=='l')
          {
             //digitalWrite(light,HIGH);
             digitalWrite(light,LOW);
             lightStatus='h';  
          }
          else
          {
             //digitalWrite(light,LOW);
             digitalWrite(light,HIGH);
             lightStatus='l';
          }
    }
    else if(choice=='3')
    {
      if(computerStatus=='l')
      {
        //digitalWrite(computer,HIGH);
        digitalWrite(computer,LOW);
        computerStatus='h';
      }
      else
      {
        //digitalWrite(computer,LOW);
        digitalWrite(computer,HIGH);
        computerStatus='l';
      }
    }
    else if(choice=='4')
    {
      if(pumpStatus=='l')
      {
        //digitalWrite(pump,HIGH);
        digitalWrite(pump,LOW);
        pumpStatus='h';
      }
      else
      {
        //digitalWrite(pump,LOW);
        digitalWrite(pump,HIGH);
        
        pumpStatus='l';
      }
    }
  }
}
