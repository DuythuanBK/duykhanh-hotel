package com.duykhanhHotel.domain.history.roomHistory.model;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedParams {
    protected Integer offset;
    protected Integer limit;

    @AssertTrue
    protected boolean getVaildPage() {
        return (offset != null && limit != null) || (offset == null && limit == null);
    }
}