package com.board.Board_Upgraded.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REFRESH_TOKEN")
@Entity
@Data
@Builder
public class RefreshToken {

    @Id
    @Column(name = "RT_KEY")
    private String key;

    @Column(name = "RT_VALUE")
    private String value;

    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}

