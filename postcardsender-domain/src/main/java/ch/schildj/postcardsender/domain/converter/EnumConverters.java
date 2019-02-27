package ch.schildj.postcardsender.domain.converter;

import ch.schildj.postcardsender.domain.enums.*;

import javax.persistence.AttributeConverter;
import java.util.function.Function;

/**
 * Converts database columns to enums
 *
 */


public interface EnumConverters {


    class PostcardStateEnumConverter implements AttributeConverter<PostcardState, Integer> {

        @Override
        public Integer convertToDatabaseColumn(PostcardState nodeStatus) {
            return nodeStatus.getCode();
        }

        @Override
        public PostcardState convertToEntityAttribute(Integer dbData) {
            return fromInt(PostcardState.values(), dbData, PostcardState::toInt);
        }
    }

    class TransmissionStateEnumConverter implements AttributeConverter<TransmissionState, Integer> {

        @Override
        public Integer convertToDatabaseColumn(TransmissionState nodeStatus) {
            return nodeStatus.getCode();
        }

        @Override
        public TransmissionState convertToEntityAttribute(Integer dbData) {
            return fromInt(TransmissionState.values(), dbData, TransmissionState::toInt);
        }
    }

    class BrandingTypeEnumConverter implements AttributeConverter<BrandingType, Integer> {

        @Override
        public Integer convertToDatabaseColumn(BrandingType nodeStatus) {
            return nodeStatus.getCode();
        }

        @Override
        public BrandingType convertToEntityAttribute(Integer dbData) {
            return fromInt(BrandingType.values(), dbData, BrandingType::toInt);
        }
    }

    class MessageTypeEnumConverter implements AttributeConverter<MessageType, Integer> {

        @Override
        public Integer convertToDatabaseColumn(MessageType nodeStatus) {
            return nodeStatus.getCode();
        }

        @Override
        public MessageType convertToEntityAttribute(Integer dbData) {
            return fromInt(MessageType.values(), dbData, MessageType::toInt);
        }
    }

    class ImageTypeEnumConverter implements AttributeConverter<ImageType, Integer> {

        @Override
        public Integer convertToDatabaseColumn(ImageType nodeStatus) {
            return nodeStatus.getCode();
        }

        @Override
        public ImageType convertToEntityAttribute(Integer dbData) {
            return fromInt(ImageType.values(), dbData, ImageType::toInt);
        }
    }

    static <E> E fromInt(E[] values, Integer integer, Function<E, Integer> toInt) {
        for (E value : values) {
            Integer valueAsInt = toInt.apply(value);
            if (valueAsInt == null) {
                return null;
            } else if (valueAsInt.equals(integer)) {
                return value;
            }
        }
        return null;
    }
}
