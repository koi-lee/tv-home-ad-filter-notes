# Skyworth TV ad filtering from the original home screen

Maintained by [koi-lee](https://github.com/koi-lee). Documentation reviewed on 2026-11-21. This is a single-device case study, not a universal smart TV ad blocker.

## What was tested?

The recorded setup is a Skyworth 75V58F Pro running Coocaa 10.01.260911 with the recorded Youku/Coolmeow player chain. It combines on-device FlClash/Mihomo domain filtering, player-specific debug properties and boot helper 0.3. Watching still starts from the original TV home screen with the remote. Background apps and initial computer-assisted setup are required.

Specified samples had no visible pre-roll video ads or ad countdowns. User-reported startup time was about 14–18 seconds after earlier waits around 35 seconds. Reboot and physical power-cycle recovery were recorded on this device. These are limited observations, not a guaranteed latency or blocking rate. See the dated [validation record](validation.md) and [follow-up tests](agent-review.md).

## Limits and safety

- No VIP unlocking, payment bypass or changes to content access rights.
- Other models, firmware versions and player providers require separate assessment. Do not copy player properties based on brand alone.
- The helper does not provide a model gate or complete rule health check. Returning home does not prove filtering is healthy.
- First-time VPN consent and vendor background permissions require local user action.
- The historical Mac-offline test was not repeated for the latest build. Continuous log collection needs a connected computer.

## Start here

1. Read the Chinese [beginner guide](quick-start.md) and [illustrated setup](illustrated-setup.md).
2. Ask your authorized Agent to follow [AGENTS.md](../AGENTS.md) and the [device workflow](agent-workflow.md).
3. Download the [tested release](https://github.com/koi-lee/tv-home-ad-filter-notes/releases/tag/v0.3-tested), verify checksums, and confirm device compatibility before installation.
4. Keep original settings for [rollback](setup.md); validate video playback, ads, restarts and power cycles separately.

Source, documentation and examples use [MIT](../LICENSE). Third-party projects retain their own licenses. For attribution, use [CITATION.cff](../CITATION.cff) and a commit permalink; retain the device and validation limits when quoting results.
