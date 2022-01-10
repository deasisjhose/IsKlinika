package com.capstone.capstone_group.isklinika;

public class ClassDateConvert {

    private String date ;
    private String month, day, year ;


    public ClassDateConvert(String materialDate){
        this.date = materialDate ;
        convertDateMonth();
        if(materialDate.length() == 11)
            convert11();
        else
            convert12();
    }

    public ClassDateConvert(String constraintDate, int different){
        this.date = constraintDate ;
        this.month = date.substring(5,6) ;
        this.day = date.substring(8, 9) ;
        this.year = date.substring(0, 3) ;
    }

    public ClassDateConvert(int chr, String date){
        this.date = date ;
        reverseConvertMonth();
        reverseConvert();
    }

    public void convertDateMonth(){
        String wordMonth = date.substring(0,3);

        switch (wordMonth){
            case "Jan":
                month = "01" ;
                break;
            case "Feb":
                month = "02" ;
                break;
            case "Mar":
                month = "03" ;
                break;
            case "Apr":
                month = "04" ;
                break;
            case "May":
                month = "05" ;
                break;
            case "Jun":
                month = "06" ;
                break;
            case "Jul":
                month = "07" ;
                break;
            case "Aug":
                month = "08" ;
                break;
            case "Sep":
                month = "09" ;
                break;
            case "Oct":
                month = "10" ;
                break;
            case "Nov":
                month = "11" ;
                break;
            case "Dec":
                month = "12" ;
                break;
        }
    }

    public void convert12(){
        this.day = date.substring(4 ,6) ;
        this.year = date.substring(8, 12) ;
    }

    public void convert11(){
        this.day = "0"+ date.substring(4 ,5) ;
        this.year = date.substring(7, 11) ;
    }

    public void reverseConvertMonth(){
        String wordMonth = date.substring(5,7);

        switch (wordMonth){
            case "01":
                month = "Jan" ;
                break;
            case "02":
                month = "Feb" ;
                break;
            case "03":
                month = "Mar" ;
                break;
            case "04":
                month = "Apr" ;
                break;
            case "05":
                month = "May" ;
                break;
            case "06":
                month = "Jun" ;
                break;
            case "07":
                month = "Jul" ;
                break;
            case "08":
                month = "Aug" ;
                break;
            case "09":
                month = "Sept" ;
                break;
            case "10":
                month = "Oct" ;
                break;
            case "11":
                month = "Nov" ;
                break;
            case "12":
                month = "Dec" ;
                break;
        }
    }

    public void reverseConvert11(){

    }
    public void reverseConvert(){
        this.year = date.substring(0,4) ;
        this.day = date.substring(8, 10);
    }

    public String getReversed(){
        return month + " " + day +"," + year ;
    }

    public String getConverted(){
        return year + "-" + month + "-" + day ;
    }

    public int getMonth() {
        return Integer.parseInt(month);
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return Integer.parseInt(day);
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getYear() {
        return Integer.parseInt(year);
    }

    public void setYear(String year) {
        this.year = year;
    }


//    public static void main(String[] args) {
//        ClassDateConvert dateConvert = new ClassDateConvert(0, "2021-01-01") ;
//
//        System.out.println(dateConvert.getReversed());
//    }
}
