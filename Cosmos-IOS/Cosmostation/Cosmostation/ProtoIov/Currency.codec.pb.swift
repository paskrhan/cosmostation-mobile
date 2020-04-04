// DO NOT EDIT.
//
// Generated by the Swift generator plugin for the protocol buffer compiler.
// Source: x/currency/codec.proto
//
// For information on using the generated types, please see the documentation:
//   https://github.com/apple/swift-protobuf/

import Foundation
import SwiftProtobuf

// If the compiler emits an error on this type, it is because this file
// was generated by a version of the `protoc` Swift plug-in that is
// incompatible with the version of SwiftProtobuf to which you are linking.
// Please ensure that your are building against the same version of the API
// that was used to generate this file.
fileprivate struct _GeneratedWithProtocGenSwiftVersion: SwiftProtobuf.ProtobufAPIVersionCheck {
  struct _2: SwiftProtobuf.ProtobufAPIVersion_2 {}
  typealias Version = _2
}

/// TokenInfo contains information about a single currency. It is used as an
/// alternative solution to hardcoding supported currencies information.
struct Currency_TokenInfo {
  // SwiftProtobuf.Message conformance is added in an extension below. See the
  // `Message` and `Message+*Additions` files in the SwiftProtobuf library for
  // methods supported on all messages.

  var metadata: Weave_Metadata {
    get {return _storage._metadata ?? Weave_Metadata()}
    set {_uniqueStorage()._metadata = newValue}
  }
  /// Returns true if `metadata` has been explicitly set.
  var hasMetadata: Bool {return _storage._metadata != nil}
  /// Clears the value of `metadata`. Subsequent reads from it will return its default value.
  mutating func clearMetadata() {_uniqueStorage()._metadata = nil}

  var name: String {
    get {return _storage._name}
    set {_uniqueStorage()._name = newValue}
  }

  var unknownFields = SwiftProtobuf.UnknownStorage()

  init() {}

  fileprivate var _storage = _StorageClass.defaultInstance
}

/// CreateMsg will register a new currency. Ticker (currency symbol) can
/// be registered only once.
struct Currency_CreateMsg {
  // SwiftProtobuf.Message conformance is added in an extension below. See the
  // `Message` and `Message+*Additions` files in the SwiftProtobuf library for
  // methods supported on all messages.

  var metadata: Weave_Metadata {
    get {return _storage._metadata ?? Weave_Metadata()}
    set {_uniqueStorage()._metadata = newValue}
  }
  /// Returns true if `metadata` has been explicitly set.
  var hasMetadata: Bool {return _storage._metadata != nil}
  /// Clears the value of `metadata`. Subsequent reads from it will return its default value.
  mutating func clearMetadata() {_uniqueStorage()._metadata = nil}

  var ticker: String {
    get {return _storage._ticker}
    set {_uniqueStorage()._ticker = newValue}
  }

  var name: String {
    get {return _storage._name}
    set {_uniqueStorage()._name = newValue}
  }

  var unknownFields = SwiftProtobuf.UnknownStorage()

  init() {}

  fileprivate var _storage = _StorageClass.defaultInstance
}

// MARK: - Code below here is support for the SwiftProtobuf runtime.

fileprivate let _protobuf_package = "currency"

extension Currency_TokenInfo: SwiftProtobuf.Message, SwiftProtobuf._MessageImplementationBase, SwiftProtobuf._ProtoNameProviding {
  static let protoMessageName: String = _protobuf_package + ".TokenInfo"
  static let _protobuf_nameMap: SwiftProtobuf._NameMap = [
    1: .same(proto: "metadata"),
    2: .same(proto: "name"),
  ]

  fileprivate class _StorageClass {
    var _metadata: Weave_Metadata? = nil
    var _name: String = String()

    static let defaultInstance = _StorageClass()

    private init() {}

    init(copying source: _StorageClass) {
      _metadata = source._metadata
      _name = source._name
    }
  }

  fileprivate mutating func _uniqueStorage() -> _StorageClass {
    if !isKnownUniquelyReferenced(&_storage) {
      _storage = _StorageClass(copying: _storage)
    }
    return _storage
  }

  mutating func decodeMessage<D: SwiftProtobuf.Decoder>(decoder: inout D) throws {
    _ = _uniqueStorage()
    try withExtendedLifetime(_storage) { (_storage: _StorageClass) in
      while let fieldNumber = try decoder.nextFieldNumber() {
        switch fieldNumber {
        case 1: try decoder.decodeSingularMessageField(value: &_storage._metadata)
        case 2: try decoder.decodeSingularStringField(value: &_storage._name)
        default: break
        }
      }
    }
  }

  func traverse<V: SwiftProtobuf.Visitor>(visitor: inout V) throws {
    try withExtendedLifetime(_storage) { (_storage: _StorageClass) in
      if let v = _storage._metadata {
        try visitor.visitSingularMessageField(value: v, fieldNumber: 1)
      }
      if !_storage._name.isEmpty {
        try visitor.visitSingularStringField(value: _storage._name, fieldNumber: 2)
      }
    }
    try unknownFields.traverse(visitor: &visitor)
  }

  static func ==(lhs: Currency_TokenInfo, rhs: Currency_TokenInfo) -> Bool {
    if lhs._storage !== rhs._storage {
      let storagesAreEqual: Bool = withExtendedLifetime((lhs._storage, rhs._storage)) { (_args: (_StorageClass, _StorageClass)) in
        let _storage = _args.0
        let rhs_storage = _args.1
        if _storage._metadata != rhs_storage._metadata {return false}
        if _storage._name != rhs_storage._name {return false}
        return true
      }
      if !storagesAreEqual {return false}
    }
    if lhs.unknownFields != rhs.unknownFields {return false}
    return true
  }
}

extension Currency_CreateMsg: SwiftProtobuf.Message, SwiftProtobuf._MessageImplementationBase, SwiftProtobuf._ProtoNameProviding {
  static let protoMessageName: String = _protobuf_package + ".CreateMsg"
  static let _protobuf_nameMap: SwiftProtobuf._NameMap = [
    1: .same(proto: "metadata"),
    2: .same(proto: "ticker"),
    3: .same(proto: "name"),
  ]

  fileprivate class _StorageClass {
    var _metadata: Weave_Metadata? = nil
    var _ticker: String = String()
    var _name: String = String()

    static let defaultInstance = _StorageClass()

    private init() {}

    init(copying source: _StorageClass) {
      _metadata = source._metadata
      _ticker = source._ticker
      _name = source._name
    }
  }

  fileprivate mutating func _uniqueStorage() -> _StorageClass {
    if !isKnownUniquelyReferenced(&_storage) {
      _storage = _StorageClass(copying: _storage)
    }
    return _storage
  }

  mutating func decodeMessage<D: SwiftProtobuf.Decoder>(decoder: inout D) throws {
    _ = _uniqueStorage()
    try withExtendedLifetime(_storage) { (_storage: _StorageClass) in
      while let fieldNumber = try decoder.nextFieldNumber() {
        switch fieldNumber {
        case 1: try decoder.decodeSingularMessageField(value: &_storage._metadata)
        case 2: try decoder.decodeSingularStringField(value: &_storage._ticker)
        case 3: try decoder.decodeSingularStringField(value: &_storage._name)
        default: break
        }
      }
    }
  }

  func traverse<V: SwiftProtobuf.Visitor>(visitor: inout V) throws {
    try withExtendedLifetime(_storage) { (_storage: _StorageClass) in
      if let v = _storage._metadata {
        try visitor.visitSingularMessageField(value: v, fieldNumber: 1)
      }
      if !_storage._ticker.isEmpty {
        try visitor.visitSingularStringField(value: _storage._ticker, fieldNumber: 2)
      }
      if !_storage._name.isEmpty {
        try visitor.visitSingularStringField(value: _storage._name, fieldNumber: 3)
      }
    }
    try unknownFields.traverse(visitor: &visitor)
  }

  static func ==(lhs: Currency_CreateMsg, rhs: Currency_CreateMsg) -> Bool {
    if lhs._storage !== rhs._storage {
      let storagesAreEqual: Bool = withExtendedLifetime((lhs._storage, rhs._storage)) { (_args: (_StorageClass, _StorageClass)) in
        let _storage = _args.0
        let rhs_storage = _args.1
        if _storage._metadata != rhs_storage._metadata {return false}
        if _storage._ticker != rhs_storage._ticker {return false}
        if _storage._name != rhs_storage._name {return false}
        return true
      }
      if !storagesAreEqual {return false}
    }
    if lhs.unknownFields != rhs.unknownFields {return false}
    return true
  }
}