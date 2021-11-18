import 'dart:typed_data';

import 'package:flutter/foundation.dart';
import 'package:qaul_rpc/qaul_rpc.dart';
import 'package:qaul_rpc/src/generated/rpc/qaul_rpc.pbenum.dart';
import 'package:qaul_rpc/src/rpc/rpc_module.dart';
import 'package:riverpod/riverpod.dart';

import '../generated/services/feed/feed.pb.dart' as pb;
import '../models/feed_message.dart' as models;

class RpcFeed extends RpcModule {
  RpcFeed(Reader read) : super(read);

  @override
  Modules get type => Modules.FEED;

  @override
  Future<void> decodeReceivedMessage(List<int> bytes) async {
    final message = pb.Feed.fromBuffer(bytes);

    debugPrint(
        '$runtimeType: ${message.whichMessage().toString()} message received');
    switch (message.whichMessage()) {
      case pb.Feed_Message.received:
        final newMessages = message.ensureReceived().feedMessage;
        debugPrint('Feed Messages: ' + newMessages.toString());

        final messagesProvider = read(feedMessagesProvider.notifier);

        for (final m in newMessages) {
          if (messagesProvider.contains(m)) continue;
          messagesProvider.add(m.toModelMessage);
        }
        break;
      default:
        throw UnhandledRpcMessageException.value(
          message.whichMessage().toString(),
          runtimeType.toString(),
        );
    }
  }

  Future<void> sendFeedMessage(String content) async {
    final msg = pb.Feed(send: pb.SendMessage(content: content));
    await encodeAndSendMessage(msg);
  }

  Future<void> requestFeedMessages({List<int>? lastReceived}) async {
    final msg =
        pb.Feed(request: pb.FeedMessageRequest(lastReceived: lastReceived));
    await encodeAndSendMessage(msg);
  }
}

// Maybe using a Stream would be simpler. Just creating this class to facilitate manipulating StateNotifierProvider
class FeedMessages extends StateNotifier<List<models.FeedMessage>> {
  FeedMessages({List<models.FeedMessage>? messages}) : super(messages ?? []);

  void add(models.FeedMessage message) {
    state = [message, ...state];
  }

  bool contains(pb.FeedMessage message) {
    return !state
        .indexWhere(
          (m) =>
      m.senderIdBase58 == message.senderIdBase58 &&
          m.messageIdBase58 == message.messageIdBase58,
    )
        .isNegative;
  }
}

extension _FMExtension on pb.FeedMessage {
  models.FeedMessage get toModelMessage => models.FeedMessage(
        senderId: Uint8List.fromList(senderId),
        senderIdBase58: senderIdBase58,
        messageId: Uint8List.fromList(messageId),
        messageIdBase58: messageIdBase58,
        timeSent: timeSent,
        timeReceived: timeReceived,
        content: content,
      );
}
