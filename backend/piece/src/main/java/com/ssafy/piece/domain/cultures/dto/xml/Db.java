package com.ssafy.piece.domain.cultures.dto.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "db")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Db {

    @XmlElement(name = "mt20id")
    private String mt20id; // 키

    @XmlElement(name = "prfnm")
    private String prfnm; // 제목

    @XmlElement(name = "prfpdfrom")
    private String prfpdfrom; //개봉일

    @XmlElement(name = "prfruntime")
    private String prfruntime; //런타임

    @XmlElement(name = "sty")
    private String sty; // 줄거리

    @XmlElement(name = "poster")
    private String poster; //포스터

    @XmlElement(name = "prfcast")
    private String prfcast; //출연진 (,로 다중 값 제공)

}
