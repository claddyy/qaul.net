//Generated by the protocol buffer compiler. DO NOT EDIT!
// source: connections/ble/ble.proto

package qaul.sys.ble;

@kotlin.jvm.JvmSynthetic
public inline fun bleStopResult(block: qaul.sys.ble.BleStopResultKt.Dsl.() -> kotlin.Unit): qaul.sys.ble.BleOuterClass.BleStopResult =
  qaul.sys.ble.BleStopResultKt.Dsl._create(qaul.sys.ble.BleOuterClass.BleStopResult.newBuilder()).apply { block() }._build()
public object BleStopResultKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: qaul.sys.ble.BleOuterClass.BleStopResult.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: qaul.sys.ble.BleOuterClass.BleStopResult.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): qaul.sys.ble.BleOuterClass.BleStopResult = _builder.build()

    /**
     * <pre>
     * whether the device was successfully stopped
     * </pre>
     *
     * <code>bool success = 1;</code>
     */
    public var success: kotlin.Boolean
      @JvmName("getSuccess")
      get() = _builder.getSuccess()
      @JvmName("setSuccess")
      set(value) {
        _builder.setSuccess(value)
      }
    /**
     * <pre>
     * whether the device was successfully stopped
     * </pre>
     *
     * <code>bool success = 1;</code>
     */
    public fun clearSuccess() {
      _builder.clearSuccess()
    }

    /**
     * <pre>
     * error reason
     * </pre>
     *
     * <code>.qaul.sys.ble.BleError error_reason = 2;</code>
     */
    public var errorReason: qaul.sys.ble.BleOuterClass.BleError
      @JvmName("getErrorReason")
      get() = _builder.getErrorReason()
      @JvmName("setErrorReason")
      set(value) {
        _builder.setErrorReason(value)
      }
    /**
     * <pre>
     * error reason
     * </pre>
     *
     * <code>.qaul.sys.ble.BleError error_reason = 2;</code>
     */
    public fun clearErrorReason() {
      _builder.clearErrorReason()
    }

    /**
     * <pre>
     * error message
     * </pre>
     *
     * <code>string error_message = 3;</code>
     */
    public var errorMessage: kotlin.String
      @JvmName("getErrorMessage")
      get() = _builder.getErrorMessage()
      @JvmName("setErrorMessage")
      set(value) {
        _builder.setErrorMessage(value)
      }
    /**
     * <pre>
     * error message
     * </pre>
     *
     * <code>string error_message = 3;</code>
     */
    public fun clearErrorMessage() {
      _builder.clearErrorMessage()
    }
  }
}
@kotlin.jvm.JvmSynthetic
public inline fun qaul.sys.ble.BleOuterClass.BleStopResult.copy(block: qaul.sys.ble.BleStopResultKt.Dsl.() -> kotlin.Unit): qaul.sys.ble.BleOuterClass.BleStopResult =
  qaul.sys.ble.BleStopResultKt.Dsl._create(this.toBuilder()).apply { block() }._build()