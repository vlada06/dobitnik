package com.vld.dobitnik.cqrs;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Combination implements Serializable {
    @Serial
    private static final long serialVersionUID = -1L;

    @NonNull
    private List<Integer> mainNumbers;
    @Nullable
    private List<Integer> bonusNumbers;
}
