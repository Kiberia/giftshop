package ru.war.robots.giftshop.model.dto;

public class GiftDto {
    private String senderUserGuid;

    private String acceptorUserGuid;

    private Long itemId;

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
}
