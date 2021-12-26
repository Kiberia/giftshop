package ru.war.robots.giftshop.model.dto;

import java.util.Objects;

public class GiftDto {
    private String senderUserGuid;

    private String acceptorUserGuid;

    private Long itemId;
    
    public GiftDto(String senderUserGuid, String acceptorUserGuid, Long itemId) {
        this.senderUserGuid = senderUserGuid;
        this.acceptorUserGuid = acceptorUserGuid;
        this.itemId = itemId;
    }
    
    public GiftDto() {
    }
    
    public String getSenderUserGuid() {
        return senderUserGuid;
    }

    public void setSenderUserGuid(String senderUserGuid) {
        this.senderUserGuid = senderUserGuid;
    }

    public String getAcceptorUserGuid() {
        return acceptorUserGuid;
    }

    public void setAcceptorUserGuid(String acceptorUserGuid) {
        this.acceptorUserGuid = acceptorUserGuid;
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
        GiftDto comparedDto = (GiftDto) o;
        return Objects.equals(senderUserGuid, comparedDto.senderUserGuid)
            && Objects.equals(acceptorUserGuid, comparedDto.acceptorUserGuid)
            && Objects.equals(itemId, comparedDto.itemId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(senderUserGuid, acceptorUserGuid, itemId);
    }
}
