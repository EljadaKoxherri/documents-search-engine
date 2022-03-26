package com.intelycare.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandArguments {
    String commandName;
    String[] commandOptions;
}
