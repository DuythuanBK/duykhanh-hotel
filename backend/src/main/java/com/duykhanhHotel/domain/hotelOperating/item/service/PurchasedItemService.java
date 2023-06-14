package com.duykhanhHotel.domain.hotelOperating.item.service;

import com.duykhanhHotel.domain.hotelOperating.item.dto.PurchasedItemDto;
import com.duykhanhHotel.domain.hotelOperating.item.entity.PurchasedItemEntity;
import com.duykhanhHotel.domain.hotelOperating.item.repository.PurchasedItemRepository;
import com.duykhanhHotel.domain.hotelOperating.room.entity.RoomEntity;
import com.duykhanhHotel.domain.hotelOperating.room.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PurchasedItemService {
    private final PurchasedItemRepository purchasedItemRepository;
    private final RoomRepository roomRepository;

    public PurchasedItemDto.SingleItem addPurchasedItem(String fullName, PurchasedItemDto.CreatedItem dto) {
        RoomEntity roomEntity = roomRepository.findByRoomFullName(fullName)
                .orElseThrow(() -> new RuntimeException("Room is not existed"));

        PurchasedItemEntity itemEntity = new PurchasedItemEntity();

        if(roomEntity.getStatus() == RoomEntity.RoomStatus.OCCUPIED) {
            itemEntity = convertDtoToEntity(roomEntity, dto);

            itemEntity = purchasedItemRepository.save(itemEntity);
        }

        return PurchasedItemDto.SingleItem.builder()
                .item(convertToDto(itemEntity))
                .build();
    }

    public void updatePurchasedItem(String fullName, Long id, PurchasedItemDto.UpdatedItem dto) {
        PurchasedItemEntity entity = purchasedItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase item is not existed"));

        if(dto.getQuantity() != null) {
            entity.setQuantity(dto.getQuantity());
        }

        purchasedItemRepository.save(entity);
    }

    public void deletePurchasedItem(Long id) {
        PurchasedItemEntity entity = purchasedItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase item is not existed"));

        purchasedItemRepository.delete(entity);
    }

    public List<PurchasedItemDto> getListPurchasedItem(String fullName) {
        List<PurchasedItemEntity> entities = purchasedItemRepository.findByRoomFullName(fullName);

        List<PurchasedItemDto> dtos = new ArrayList<>();

        entities.stream().forEach((entitiy) -> {
            PurchasedItemDto dto = convertToDto(entitiy);
            dtos.add(dto);
        });

        return dtos;
    }


    private PurchasedItemDto convertToDto(PurchasedItemEntity entity) {
        return PurchasedItemDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .type(entity.getType())
                .quantity(entity.getQuantity())
                .build();
    }

    private PurchasedItemEntity convertDtoToEntity(RoomEntity roomEntity, PurchasedItemDto.CreatedItem dto) {
        return PurchasedItemEntity.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .type(dto.getType())
                .room(roomEntity)
                .build();
    }
    private PurchasedItemEntity convertDtoToEntity(RoomEntity roomEntity, PurchasedItemDto dto) {
        return PurchasedItemEntity.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .type(dto.getType())
                .room(roomEntity)
                .build();
    }
}
