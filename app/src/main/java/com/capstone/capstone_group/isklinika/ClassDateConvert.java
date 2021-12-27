package com.capstone.capstone_group.isklinika;

public class ClassDateConvert {

    private String date ;
    private String month, day, year ;


    public ClassDateConvert(String materialDate){
        this.date = materialDate ;
        convertDateMonth();
        convertDateDay();
        convertDateYear();
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

    public void convertDateDay(){
        this.day = date.substring(4, 6) ;
    }

    public void convertDateYear(){
        this.year = date.substring(8, 12) ;
    }

    public String getConverted(){
        return month + "-" + day + "-" + year ;
    }



}
