package com.duykhanhHotel.domain.hotelConfig.item.service;

import com.duykhanhHotel.domain.hotelConfig.item.dto.ItemDto;
import com.duykhanhHotel.domain.hotelConfig.item.dto.ItemTypeDto;
import com.duykhanhHotel.domain.hotelConfig.item.entity.ItemEntity;
import com.duykhanhHotel.domain.hotelConfig.item.entity.ItemTypeEntity;
import com.duykhanhHotel.domain.hotelConfig.item.resipotory.ItemRepository;
import com.duykhanhHotel.domain.hotelConfig.item.resipotory.ItemTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemTypeRepository itemTypeRepository;

    public void createItemType(ItemTypeDto.Update dto) {
        if(itemTypeRepository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Item type is already exist");
        }

        ItemTypeEntity itemType = ItemTypeEntity.builder().name(dto.getName()).build();
        itemTypeRepository.save(itemType);
    }

    public void updateItemType(Long id, ItemTypeDto.Update dto) {
        ItemTypeEntity oldItemType = itemTypeRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException("Item type is not exist"));
        if(dto.getName() != null) {
            if (itemTypeRepository.findByName(dto.getName()).isPresent()) {
                throw new RuntimeException("Item type is already exist");
            }

            oldItemType.setName(dto.getName());
        }
        itemTypeRepository.save(oldItemType);
    }

    public ItemTypeDto getItemType(String name) {
        ItemTypeEntity itemTypeEntity = itemTypeRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Item type is not exist"));

        return convertItemTypeToDto(itemTypeEntity);
    }

    public ItemTypeDto getItemType(Long id) {
        ItemTypeEntity itemTypeEntity = itemTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item type is not exist"));

        return convertItemTypeToDto(itemTypeEntity);
    }
    public List<ItemTypeDto> getListItemType() {
        List<ItemTypeEntity> itemTypeEntityList =
                itemTypeRepository.findAll();

        List<ItemTypeDto> itemTypeDtoList = new ArrayList<>();

        itemTypeEntityList.stream().forEach((entity) -> {
            ItemTypeDto itemTypeDto = ItemTypeDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();

            itemTypeDtoList.add(itemTypeDto);
        });

        return itemTypeDtoList;
    }

    public void deleteItemType(Long id) {
        ItemTypeEntity itemTypeEntity = itemTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item type is not exist"));

        itemTypeRepository.delete(itemTypeEntity);
    }

    // Item
    public ItemDto.SingleItem createItem(ItemDto.UpdateItem itemDto) {
//        ItemTypeEntity itemTypeEntity = itemTypeRepository.findByName(itemDto.getType())
//                .orElseThrow(() -> new RuntimeException("Type is not exist"));

        ItemEntity itemEntity = ItemEntity.builder()
                .name(itemDto.getName())
                .price(itemDto.getPrice())
//                .itemTypeEntity(itemTypeEntity)
                .build();

        itemEntity = itemRepository.save(itemEntity);

        return ItemDto.SingleItem.builder()
                .item(convertItemtoDto(itemEntity))
                .build();
    }

    public void updateItem(Long id, ItemDto.UpdateItem itemDto) {
        ItemEntity itemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item does not exist"));

        if(itemDto.getName() != null) {
            itemEntity.setName(itemDto.getName());
        }

        if(itemDto.getPrice() != null) {
            itemEntity.setPrice(itemDto.getPrice());
        }

//        if(itemDto.getType() != null) {
//            ItemTypeEntity itemTypeEntity = itemTypeRepository.findByName(itemDto.getType())
//                    .orElseThrow(() -> new RuntimeException("Type is not exist"));
//
//            itemEntity.setItemTypeEntity(itemTypeEntity);
//        }

        itemRepository.save(itemEntity);
    }

    public ItemDto getItem(String name) {
        ItemEntity itemEntity = itemRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Item does not exist"));

        return convertItemtoDto(itemEntity);
    }

    public ItemDto getItem(Long id) {
        ItemEntity itemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item does not exist"));

        return convertItemtoDto(itemEntity);
    }

    public List<ItemDto> getListItem() {
        List<ItemEntity> items = itemRepository.findAll();

        List<ItemDto> itemDtos = new ArrayList<>();

        items.stream().forEach((entity -> {
            ItemDto itemDto = ItemDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .price(entity.getPrice())
//                    .type(entity.getItemTypeEntity().getName())
                    .build();
            itemDtos.add(itemDto);
        }));

        return itemDtos;
    }

    public void deleteItem(Long id) {
        ItemEntity itemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item does not exist"));

        itemRepository.delete(itemEntity);
    }
    private ItemTypeDto convertItemTypeToDto(ItemTypeEntity entity) {
        return ItemTypeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    private ItemDto convertItemtoDto(ItemEntity entity) {
        return ItemDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
//                .type(entity.getItemTypeEntity().getName())
                .build();
    }
}
