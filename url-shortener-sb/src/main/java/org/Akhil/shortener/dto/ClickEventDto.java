package org.Akhil.shortener.dto;

import java.time.LocalDate;

public class ClickEventDto {
    private LocalDate clickDate;
    private Long count;

    public LocalDate getClickDate() {
        return clickDate;
    }

    public void setClickDate(LocalDate clickDate) {
        this.clickDate = clickDate;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ClickEventDto{" +
                "clickDate=" + clickDate +
                ", count=" + count +
                '}';
    }
}
