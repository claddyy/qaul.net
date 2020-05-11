//! # RPC Test Environment with 3 Node Simulation
//! 
//! The test environment provides a ThreeNode local 
//! network which is based on the ratman-harness crate.
//! With it, an entire local 3 node network is simulated
//! and can be used to run the RPC tests.

#[cfg(test)]
pub(crate) mod rpc_harness {
    use async_std::sync::Arc;
    use libqaul_rpc::{
        json::{RequestEnv, ResponseEnv},
        Envelope, Responder,
    };
    use ratman_harness::{temp, Initialize, ThreePoint};
    use {
        qaul_chat::Chat,
        qaul_voices::Voices,
    }; 
    use libqaul::{Qaul, Identity};

    /// RPC test state
    pub(crate) struct RPC {
        pub responder_a: Responder,
        pub responder_b: Responder,
        pub network: ThreePoint<Arc<Qaul>>,
    }

    impl RPC {
        /// initialized the RPC test environment
        pub(crate) async fn init() -> RPC {
            // Initialize a basic libqaul stack with no interfaces
            let mut tp = ThreePoint::new().await;
            tp.init_with(|_, arc| Qaul::new(arc, temp().path()));

            // services for Node A
            let tp_a = tp.a.clone();
            let qaul_a = tp_a.1.unwrap().clone();
            let chat_a = Chat::new(Arc::clone(&qaul_a)).await.unwrap();
            let voices_a = Voices::new(Arc::clone(&qaul_a)).await.unwrap();

            // services for Node B
            let tp_b = tp.b.clone();
            let qaul_b = tp_b.1.unwrap().clone();
            let chat_b = Chat::new(Arc::clone(&qaul_b)).await.unwrap();
            let voices_b = Voices::new(Arc::clone(&qaul_b)).await.unwrap();
            
            RPC {
                responder_a: Responder {qaul: qaul_a, chat: chat_a, voices: voices_a},
                responder_b: Responder {qaul: qaul_b, chat: chat_b, voices: voices_b},
                network: tp
            }
        }

        /// send a RPC call through Node A
        pub(crate) async fn send_a(self, json_string: &str) -> ResponseEnv {
            self.send(json_string, 1).await
        }

        /// send a RPC call through Node B
        pub(crate) async fn send_b(self, json_string: &str) -> ResponseEnv {
            self.send(json_string, 2).await
        }

        /// send a RPC call
        pub(crate) async fn send(self, json_string: &str, node: u8) -> ResponseEnv {
            let req_env: RequestEnv =
                serde_json::from_str(json_string).unwrap();
            let Envelope { id, data: req } = req_env.clone().generate_envelope().unwrap();

            // Call into libqaul via the rpc utilities
            let resp = match node {
                1 => self.responder_a.respond(req).await,
                _ => self.responder_b.respond(req).await
            };

            let env = Envelope { id, data: resp };
            let resp_env: ResponseEnv = (env, req_env).into();
            resp_env
        }
    }
}