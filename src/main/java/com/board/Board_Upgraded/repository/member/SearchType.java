package com.board.Board_Upgraded.repository.member;

public enum SearchType {

    LIKE("like"), EXACT("exact");

    private String type;

    private SearchType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
