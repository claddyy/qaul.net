//Generated by the protocol buffer compiler. DO NOT EDIT!
// source: router/router.proto

package qaul.rpc.router;

@kotlin.jvm.JvmSynthetic
inline fun neighboursRequest(block: qaul.rpc.router.NeighboursRequestKt.Dsl.() -> Unit): qaul.rpc.router.RouterOuterClass.NeighboursRequest =
  qaul.rpc.router.NeighboursRequestKt.Dsl._create(qaul.rpc.router.RouterOuterClass.NeighboursRequest.newBuilder()).apply { block() }._build()
object NeighboursRequestKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  class Dsl private constructor(
    @kotlin.jvm.JvmField private val _builder: qaul.rpc.router.RouterOuterClass.NeighboursRequest.Builder
  ) {
    companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: qaul.rpc.router.RouterOuterClass.NeighboursRequest.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): qaul.rpc.router.RouterOuterClass.NeighboursRequest = _builder.build()
  }
}
@kotlin.jvm.JvmSynthetic
inline fun qaul.rpc.router.RouterOuterClass.NeighboursRequest.copy(block: qaul.rpc.router.NeighboursRequestKt.Dsl.() -> Unit): qaul.rpc.router.RouterOuterClass.NeighboursRequest =
  qaul.rpc.router.NeighboursRequestKt.Dsl._create(this.toBuilder()).apply { block() }._build()