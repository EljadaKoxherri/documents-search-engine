package com.intelycare.model;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentModel {
    private Integer documentId;
    private List<String> tokens;
}
