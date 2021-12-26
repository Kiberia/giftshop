package ru.war.robots.giftshop.model.dto;

import java.util.Objects;

public class BuyDto {
    private String userGuid;

    private Long itemId;
    
    public BuyDto() {}
    
    public BuyDto(String userGuid, Long itemId) {
        this.userGuid = userGuid;
        this.itemId = itemId;
    }
    
    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        BuyDto comparedDto = (BuyDto) o;
    
        return Objects.equals(userGuid, comparedDto.userGuid)
            && Objects.equals(itemId, comparedDto.itemId);
    }
    
    @Override
    public int hashCode() {
        int result = userGuid.hashCode();
        result = 31 * result + itemId.hashCode();
        return result;
    }
}
