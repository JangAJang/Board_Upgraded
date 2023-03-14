package com.board.Board_Upgraded.domain.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Title {

    @Column(name = "TITLE")
    private String title;

    public String getTitle() {
        return title;
    }
}
