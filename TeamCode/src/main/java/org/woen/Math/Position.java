package org.woen.Math;



public class Position {



        public double x;

        public double y;
        public double h;

    public Position(double x, double y, double h){
        this.x = x ;
        this.y = y ;
        this.h = h;
    }

    public Position(){
        this.x = 0;
        this.h = 0;
        this.y = 0;
    }

    public void rotateVector(double angle){
        double radians = Math.toRadians(angle);

        double y1 = Math.cos(radians) * y + Math.sin(radians) * x;
        double x1 = Math.cos(radians) * x - Math.sin(radians) * y;

        x = x1;
        y = y1;
    }

    public void minusVector(Position pos){
        x -= pos.x;
        y -= pos.y;
    }

    public void vectorPlus(Position pos) {
        x += pos.x;
        y += pos.y;
    }

    public void plusVector(Position pos){
        x += pos.x;
        y += pos.y;
    }

    public void  minusPos(Position pos){
        x -= pos.x;
        y -= pos.y;
        h -= pos.h;
    }
    public void  plusPos(Position pos){
        x += pos.x;
        y += pos.y;
        h += pos.h;
    }

    public double getLength(){
        return Math.sqrt(x*x+y*y) ;
    }

    public static double length(Position s, Position e){
        return Math.sqrt((s.x-e.x)*(s.x-e.x) - (s.y-e.y)*(s.y-e.y));
    }

}
