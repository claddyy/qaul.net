//! Endpoint abstraction module

use crate::{Frame, Result, Target};
use async_trait::async_trait;

/// The main trait describing a Ratman networking interface
#[async_trait]
pub trait Endpoint {
    /// Return a desired frame size in bytes
    ///
    /// A user of this library should use this metric to slice larger
    /// payloads into frame sequencies via the provided utilities.
    ///
    /// This metric is only a hint, and a router can choose to ignore
    /// it, if it then deals with possible "too large" errors during
    /// sending.  Choosing between a greedy or cautious approach to
    /// data slicing is left to the user of the interfaces.
    fn size_hint(&self) -> usize;

    /// Dispatch a `Frame` across this link
    ///
    /// Sending characteristics are entirely up to the implementation.
    /// As mentioned in the `size_hint()` documentation, this function
    /// **must not** panic on a `Frame` for size reasons, instead it
    /// should return `Error::FrameTooLarge`.
    ///
    /// The target ID is a way to instruct a netmod where to send a
    /// frame in a one-to-many mapping.  When implementing a
    /// one-to-one endpoint, this ID can be ignored (set to 0).
    async fn send(&mut self, frame: Frame, target: Target) -> Result<()>;

    /// Poll for the next available Frame from this interface
    ///
    /// It's recomended to return transmission errors, even if there
    /// are no ways to correct the situation from the router's POV,
    /// simply to feed packet drop metrics.
    async fn next(&mut self) -> Result<(Frame, Target)>;
}
