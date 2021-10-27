//Generated by the protocol buffer compiler. DO NOT EDIT!
// source: connections/ble/manager/ble.proto

package qaul.sys.ble;

@kotlin.jvm.JvmSynthetic
inline fun bleAdvertisingSet(block: qaul.sys.ble.BleAdvertisingSetKt.Dsl.() -> Unit): qaul.sys.ble.BleOuterClass.BleAdvertisingSet =
  qaul.sys.ble.BleAdvertisingSetKt.Dsl._create(qaul.sys.ble.BleOuterClass.BleAdvertisingSet.newBuilder()).apply { block() }._build()
object BleAdvertisingSetKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  class Dsl private constructor(
    @kotlin.jvm.JvmField private val _builder: qaul.sys.ble.BleOuterClass.BleAdvertisingSet.Builder
  ) {
    companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: qaul.sys.ble.BleOuterClass.BleAdvertisingSet.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): qaul.sys.ble.BleOuterClass.BleAdvertisingSet = _builder.build()

    /**
     * <pre>
     * set data which can be used for interval data advertisement
     * </pre>
     *
     * <code>bytes data = 1;</code>
     */
    var data: com.google.protobuf.ByteString
      @JvmName("getData")
      get() = _builder.getData()
      @JvmName("setData")
      set(value) {
        _builder.setData(value)
      }
    /**
     * <pre>
     * set data which can be used for interval data advertisement
     * </pre>
     *
     * <code>bytes data = 1;</code>
     */
    fun clearData() {
      _builder.clearData()
    }
  }
}
@kotlin.jvm.JvmSynthetic
inline fun qaul.sys.ble.BleOuterClass.BleAdvertisingSet.copy(block: qaul.sys.ble.BleAdvertisingSetKt.Dsl.() -> Unit): qaul.sys.ble.BleOuterClass.BleAdvertisingSet =
  qaul.sys.ble.BleAdvertisingSetKt.Dsl._create(this.toBuilder()).apply { block() }._build()
