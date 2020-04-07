use crate::{
    json::{JsonAuth, JsonMap, RequestEnv},
    Envelope, Request,
};
use libqaul::users::UserAuth;
use serde::de::DeserializeOwned;

#[derive(Debug)]
pub(crate) struct AuthInject<'env> {
    auth: Option<JsonAuth>,
    method: &'env str,
    kind: &'env str,
}

/// Inject auth info, and turn the data map into a concrete object
///
/// The way we do this here should hopefully be optimisable enough,
/// because we don't otherwise touch anything and rustc is a smart
/// cookie, but this function might still break down with larger
/// payloads and need more optimisations.
fn de_json<'env, T: DeserializeOwned>(mut data: JsonMap, auth: AuthInject<'env>) -> T {
    dbg!(&auth);
    match (auth.kind, auth.method) {
        // We don't want to inject the auth info for a few cases
        ("users", "list")
        | ("users", "login")
        | ("users", "create")
        | ("users", "get")
        | ("files", "list") => {}
        (_, _) => {
            data.insert(
                "auth".into(),
                serde_json::to_value(UserAuth::from(auth.auth.unwrap()))
                    .expect("Failed to inject auth"),
            );
        }
    };

    serde_json::from_value(serde_json::to_value(&data).unwrap())
        .expect(&format!("Failed to parse websocket payload `{:#?}`", &data))
}

impl From<RequestEnv> for Envelope<Request> {
    fn from(je: RequestEnv) -> Self {
        let RequestEnv {
            id,
            page: _,
            method,
            kind,
            auth,
            data,
        } = je; // m'apelle janette

        let auth = AuthInject {
            auth,
            kind: &kind,
            method: &method,
        };

        Envelope {
            id,
            data: match (kind.as_str(), method.as_str()) {
                // chat service message functions
                //#[cfg(features = "chat")]
                ("chat-messages", "next") => Request::ChatMsgNext(de_json(data, auth)),
                // #[cfg(features = "chat")]
                // ("chat-messages", "subscribe") => Request::ChatMsgSub(de_json(data, auth)),
                //#[cfg(features = "chat")]
                ("chat-messages", "create") => Request::ChatMsgSend(de_json(data, auth)),
                #[cfg(features = "chat")]
                ("chat-messages", "query") => Request::ChatMsgQuery(de_json(data, auth)),

                // chat service room management
                //#[cfg(features = "chat")]
                ("chat-rooms", "list") => Request::ChatRoomList(de_json(data, auth)),
                //#[cfg(features = "chat")]
                ("chat-rooms", "get") => Request::ChatRoomGet(de_json(data, auth)),
                //#[cfg(features = "chat")]
                ("chat-rooms", "create") => Request::ChatRoomCreate(de_json(data, auth)),
                //#[cfg(features = "chat")]
                ("chat-rooms", "modify") => Request::ChatRoomModify(de_json(data, auth)),
                //#[cfg(features = "chat")]
                ("chat-rooms", "delete") => Request::ChatRoomDelete(de_json(data, auth)),

                // libqaul contact functions
                ("contacts", "list") => Request::ContactAll(de_json(data, auth)),
                ("contacts", "get") => Request::ContactGet(de_json(data, auth)),
                ("contacts", "query") => Request::ContactQuery(de_json(data, auth)),
                ("contacts", "modify") => Request::ContactQuery(de_json(data, auth)),

                // libqaul user functions
                ("users", "list") => Request::UserList(de_json(data, auth)),
                ("users", "create") => Request::UserCreate(de_json(data, auth)),
                ("users", "delete") => Request::UserDelete(de_json(data, auth)),
                ("users", "repass") => Request::UserChangePw(de_json(data, auth)),
                ("users", "login") => Request::UserLogin(de_json(data, auth)),
                ("users", "logout") => Request::UserLogout(de_json(data, auth)),
                ("users", "get") => Request::UserGet(de_json(data, auth)),
                ("users", "modify") => Request::UserUpdate(de_json(data, auth)),
                (kind, method) => panic!(format!("Unknown parse tuple: ({}, {})", kind, method)),
            },
        }
    }
}

#[test]
#[cfg(features = "chat")]
fn envelope_chat_message_next() {
    // This re-uses the same ID for auth and room data not because
    // it's in any way significant but rather because it's 3am and I'm
    // being lazy
    let json = r#"{ "id": "1", 
                    "auth": { "id": "1C56 105D 52C3 D617  2603 D69F 9E0F 93AE", "token": "token" }, 
                    "kind": "chat-messages", 
                    "method": "next", 
                    "data": { "room": "1C56 105D 52C3 D617  2603 D69F 9E0F 93AE" } }"#;

    let je: RequestEnv = serde_json::from_str(&json).expect("JsonEnvelope failed");
    let env: Envelope = je.into();

    if let EnvelopeType::Request(Request::ChatMsgNext(msg)) = env.data {
        assert_eq!(
            msg.auth.0.to_string(),
            "1C56 105D 52C3 D617  2603 D69F 9E0F 93AE"
        );
    } else {
        panic!("Failed to deserialise correctly")
    }
}

/// This test checks if an empty body request can be handled
///
/// This pins the layout of User::List, which can't ever be a
/// zero-sized struct, but must have an empty body because serde_json
/// get's sad otherwise.
#[test]
fn envelope_chat_user_list() {
    let json = r#"{ "id": "1", 
                    "kind": "users", 
                    "method": "list" }"#;

    let je: RequestEnv = serde_json::from_str(&json).expect("JsonEnvelope failed");
    let _env: Envelope<Request> = je.into();
}
