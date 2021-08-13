//Generated by the protocol buffer compiler. DO NOT EDIT!
// source: router/router.proto

package qaul.rpc.router;

@kotlin.jvm.JvmSynthetic
inline fun router(block: qaul.rpc.router.RouterKt.Dsl.() -> Unit): qaul.rpc.router.RouterOuterClass.Router =
  qaul.rpc.router.RouterKt.Dsl._create(qaul.rpc.router.RouterOuterClass.Router.newBuilder()).apply { block() }._build()
object RouterKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  class Dsl private constructor(
    @kotlin.jvm.JvmField private val _builder: qaul.rpc.router.RouterOuterClass.Router.Builder
  ) {
    companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: qaul.rpc.router.RouterOuterClass.Router.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): qaul.rpc.router.RouterOuterClass.Router = _builder.build()

    /**
     * <code>.qaul.rpc.router.UserRequest user_request = 1;</code>
     */
    var userRequest: qaul.rpc.router.RouterOuterClass.UserRequest
      @JvmName("getUserRequest")
      get() = _builder.getUserRequest()
      @JvmName("setUserRequest")
      set(value) {
        _builder.setUserRequest(value)
      }
    /**
     * <code>.qaul.rpc.router.UserRequest user_request = 1;</code>
     */
    fun clearUserRequest() {
      _builder.clearUserRequest()
    }
    /**
     * <code>.qaul.rpc.router.UserRequest user_request = 1;</code>
     * @return Whether the userRequest field is set.
     */
    fun hasUserRequest(): kotlin.Boolean {
      return _builder.hasUserRequest()
    }

    /**
     * <code>.qaul.rpc.router.UserList user_list = 2;</code>
     */
    var userList: qaul.rpc.router.RouterOuterClass.UserList
      @JvmName("getUserList")
      get() = _builder.getUserList()
      @JvmName("setUserList")
      set(value) {
        _builder.setUserList(value)
      }
    /**
     * <code>.qaul.rpc.router.UserList user_list = 2;</code>
     */
    fun clearUserList() {
      _builder.clearUserList()
    }
    /**
     * <code>.qaul.rpc.router.UserList user_list = 2;</code>
     * @return Whether the userList field is set.
     */
    fun hasUserList(): kotlin.Boolean {
      return _builder.hasUserList()
    }
    val messageCase: qaul.rpc.router.RouterOuterClass.Router.MessageCase
      @JvmName("getMessageCase")
      get() = _builder.getMessageCase()

    fun clearMessage() {
      _builder.clearMessage()
    }
  }
}
@kotlin.jvm.JvmSynthetic
inline fun qaul.rpc.router.RouterOuterClass.Router.copy(block: qaul.rpc.router.RouterKt.Dsl.() -> Unit): qaul.rpc.router.RouterOuterClass.Router =
  qaul.rpc.router.RouterKt.Dsl._create(this.toBuilder()).apply { block() }._build()