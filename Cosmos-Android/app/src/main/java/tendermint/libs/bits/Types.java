// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tendermint/libs/bits/types.proto

package tendermint.libs.bits;

public final class Types {
  private Types() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface BitArrayOrBuilder extends
      // @@protoc_insertion_point(interface_extends:tendermint.libs.bits.BitArray)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int64 bits = 1;</code>
     * @return The bits.
     */
    long getBits();

    /**
     * <code>repeated uint64 elems = 2;</code>
     * @return A list containing the elems.
     */
    java.util.List<java.lang.Long> getElemsList();
    /**
     * <code>repeated uint64 elems = 2;</code>
     * @return The count of elems.
     */
    int getElemsCount();
    /**
     * <code>repeated uint64 elems = 2;</code>
     * @param index The index of the element to return.
     * @return The elems at the given index.
     */
    long getElems(int index);
  }
  /**
   * Protobuf type {@code tendermint.libs.bits.BitArray}
   */
  public  static final class BitArray extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:tendermint.libs.bits.BitArray)
      BitArrayOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use BitArray.newBuilder() to construct.
    private BitArray(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private BitArray() {
      elems_ = emptyLongList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new BitArray();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private BitArray(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              bits_ = input.readInt64();
              break;
            }
            case 16: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                elems_ = newLongList();
                mutable_bitField0_ |= 0x00000001;
              }
              elems_.addLong(input.readUInt64());
              break;
            }
            case 18: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
                elems_ = newLongList();
                mutable_bitField0_ |= 0x00000001;
              }
              while (input.getBytesUntilLimit() > 0) {
                elems_.addLong(input.readUInt64());
              }
              input.popLimit(limit);
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) != 0)) {
          elems_.makeImmutable(); // C
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return tendermint.libs.bits.Types.internal_static_tendermint_libs_bits_BitArray_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return tendermint.libs.bits.Types.internal_static_tendermint_libs_bits_BitArray_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              tendermint.libs.bits.Types.BitArray.class, tendermint.libs.bits.Types.BitArray.Builder.class);
    }

    public static final int BITS_FIELD_NUMBER = 1;
    private long bits_;
    /**
     * <code>int64 bits = 1;</code>
     * @return The bits.
     */
    public long getBits() {
      return bits_;
    }

    public static final int ELEMS_FIELD_NUMBER = 2;
    private com.google.protobuf.Internal.LongList elems_;
    /**
     * <code>repeated uint64 elems = 2;</code>
     * @return A list containing the elems.
     */
    public java.util.List<java.lang.Long>
        getElemsList() {
      return elems_;
    }
    /**
     * <code>repeated uint64 elems = 2;</code>
     * @return The count of elems.
     */
    public int getElemsCount() {
      return elems_.size();
    }
    /**
     * <code>repeated uint64 elems = 2;</code>
     * @param index The index of the element to return.
     * @return The elems at the given index.
     */
    public long getElems(int index) {
      return elems_.getLong(index);
    }
    private int elemsMemoizedSerializedSize = -1;

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (bits_ != 0L) {
        output.writeInt64(1, bits_);
      }
      if (getElemsList().size() > 0) {
        output.writeUInt32NoTag(18);
        output.writeUInt32NoTag(elemsMemoizedSerializedSize);
      }
      for (int i = 0; i < elems_.size(); i++) {
        output.writeUInt64NoTag(elems_.getLong(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (bits_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, bits_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < elems_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeUInt64SizeNoTag(elems_.getLong(i));
        }
        size += dataSize;
        if (!getElemsList().isEmpty()) {
          size += 1;
          size += com.google.protobuf.CodedOutputStream
              .computeInt32SizeNoTag(dataSize);
        }
        elemsMemoizedSerializedSize = dataSize;
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof tendermint.libs.bits.Types.BitArray)) {
        return super.equals(obj);
      }
      tendermint.libs.bits.Types.BitArray other = (tendermint.libs.bits.Types.BitArray) obj;

      if (getBits()
          != other.getBits()) return false;
      if (!getElemsList()
          .equals(other.getElemsList())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + BITS_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getBits());
      if (getElemsCount() > 0) {
        hash = (37 * hash) + ELEMS_FIELD_NUMBER;
        hash = (53 * hash) + getElemsList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static tendermint.libs.bits.Types.BitArray parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static tendermint.libs.bits.Types.BitArray parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static tendermint.libs.bits.Types.BitArray parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static tendermint.libs.bits.Types.BitArray parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static tendermint.libs.bits.Types.BitArray parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static tendermint.libs.bits.Types.BitArray parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static tendermint.libs.bits.Types.BitArray parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static tendermint.libs.bits.Types.BitArray parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static tendermint.libs.bits.Types.BitArray parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static tendermint.libs.bits.Types.BitArray parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static tendermint.libs.bits.Types.BitArray parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static tendermint.libs.bits.Types.BitArray parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(tendermint.libs.bits.Types.BitArray prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code tendermint.libs.bits.BitArray}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:tendermint.libs.bits.BitArray)
        tendermint.libs.bits.Types.BitArrayOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return tendermint.libs.bits.Types.internal_static_tendermint_libs_bits_BitArray_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return tendermint.libs.bits.Types.internal_static_tendermint_libs_bits_BitArray_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                tendermint.libs.bits.Types.BitArray.class, tendermint.libs.bits.Types.BitArray.Builder.class);
      }

      // Construct using tendermint.libs.bits.Types.BitArray.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bits_ = 0L;

        elems_ = emptyLongList();
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return tendermint.libs.bits.Types.internal_static_tendermint_libs_bits_BitArray_descriptor;
      }

      @java.lang.Override
      public tendermint.libs.bits.Types.BitArray getDefaultInstanceForType() {
        return tendermint.libs.bits.Types.BitArray.getDefaultInstance();
      }

      @java.lang.Override
      public tendermint.libs.bits.Types.BitArray build() {
        tendermint.libs.bits.Types.BitArray result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public tendermint.libs.bits.Types.BitArray buildPartial() {
        tendermint.libs.bits.Types.BitArray result = new tendermint.libs.bits.Types.BitArray(this);
        int from_bitField0_ = bitField0_;
        result.bits_ = bits_;
        if (((bitField0_ & 0x00000001) != 0)) {
          elems_.makeImmutable();
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.elems_ = elems_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof tendermint.libs.bits.Types.BitArray) {
          return mergeFrom((tendermint.libs.bits.Types.BitArray)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(tendermint.libs.bits.Types.BitArray other) {
        if (other == tendermint.libs.bits.Types.BitArray.getDefaultInstance()) return this;
        if (other.getBits() != 0L) {
          setBits(other.getBits());
        }
        if (!other.elems_.isEmpty()) {
          if (elems_.isEmpty()) {
            elems_ = other.elems_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureElemsIsMutable();
            elems_.addAll(other.elems_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        tendermint.libs.bits.Types.BitArray parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (tendermint.libs.bits.Types.BitArray) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private long bits_ ;
      /**
       * <code>int64 bits = 1;</code>
       * @return The bits.
       */
      public long getBits() {
        return bits_;
      }
      /**
       * <code>int64 bits = 1;</code>
       * @param value The bits to set.
       * @return This builder for chaining.
       */
      public Builder setBits(long value) {
        
        bits_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 bits = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearBits() {
        
        bits_ = 0L;
        onChanged();
        return this;
      }

      private com.google.protobuf.Internal.LongList elems_ = emptyLongList();
      private void ensureElemsIsMutable() {
        if (!((bitField0_ & 0x00000001) != 0)) {
          elems_ = mutableCopy(elems_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
       * <code>repeated uint64 elems = 2;</code>
       * @return A list containing the elems.
       */
      public java.util.List<java.lang.Long>
          getElemsList() {
        return ((bitField0_ & 0x00000001) != 0) ?
                 java.util.Collections.unmodifiableList(elems_) : elems_;
      }
      /**
       * <code>repeated uint64 elems = 2;</code>
       * @return The count of elems.
       */
      public int getElemsCount() {
        return elems_.size();
      }
      /**
       * <code>repeated uint64 elems = 2;</code>
       * @param index The index of the element to return.
       * @return The elems at the given index.
       */
      public long getElems(int index) {
        return elems_.getLong(index);
      }
      /**
       * <code>repeated uint64 elems = 2;</code>
       * @param index The index to set the value at.
       * @param value The elems to set.
       * @return This builder for chaining.
       */
      public Builder setElems(
          int index, long value) {
        ensureElemsIsMutable();
        elems_.setLong(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint64 elems = 2;</code>
       * @param value The elems to add.
       * @return This builder for chaining.
       */
      public Builder addElems(long value) {
        ensureElemsIsMutable();
        elems_.addLong(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint64 elems = 2;</code>
       * @param values The elems to add.
       * @return This builder for chaining.
       */
      public Builder addAllElems(
          java.lang.Iterable<? extends java.lang.Long> values) {
        ensureElemsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, elems_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated uint64 elems = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearElems() {
        elems_ = emptyLongList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:tendermint.libs.bits.BitArray)
    }

    // @@protoc_insertion_point(class_scope:tendermint.libs.bits.BitArray)
    private static final tendermint.libs.bits.Types.BitArray DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new tendermint.libs.bits.Types.BitArray();
    }

    public static tendermint.libs.bits.Types.BitArray getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<BitArray>
        PARSER = new com.google.protobuf.AbstractParser<BitArray>() {
      @java.lang.Override
      public BitArray parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new BitArray(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<BitArray> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<BitArray> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public tendermint.libs.bits.Types.BitArray getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_tendermint_libs_bits_BitArray_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_tendermint_libs_bits_BitArray_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n tendermint/libs/bits/types.proto\022\024tend" +
      "ermint.libs.bits\"\'\n\010BitArray\022\014\n\004bits\030\001 \001" +
      "(\003\022\r\n\005elems\030\002 \003(\004B=Z;github.com/tendermi" +
      "nt/tendermint/proto/tendermint/libs/bits" +
      "b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_tendermint_libs_bits_BitArray_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_tendermint_libs_bits_BitArray_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_tendermint_libs_bits_BitArray_descriptor,
        new java.lang.String[] { "Bits", "Elems", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
