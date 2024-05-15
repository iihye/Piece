package com.ssafy.piece.domain.cultures.dto.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "dbs")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KopisResponse {

    @XmlElement(name = "db")
    private Db db;

}
