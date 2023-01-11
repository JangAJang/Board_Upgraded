package com.board.Board_Upgraded.entity.base;

public enum DueTime {

    PASSWORD_CHANGE_DUETIME(30L);

    private Long days;

    private DueTime(Long days){
        this.days = days;
    }

    public Long getDays(){
        return this.days;
    }
}
