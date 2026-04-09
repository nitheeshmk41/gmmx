# Mobile UI Next TODOs

Last updated: 2026-04-09

## Current state
- App theme direction has been aligned to the provided design samples.
- Dashboard and Attendance pages were redesigned to match the new visual language.
- App shell bottom navigation was redesigned.
- App can run successfully on Chrome from the mobile project folder.

## How to resume quickly
1. Open terminal in project root.
2. Run: `cd mobile; flutter run -d chrome`
3. For hot reload after edits: `r`
4. For hot restart: `R`

## Pending UI work by module

### Module 3 - Trainer Management
- Restyle trainer list page to new premium visual system.
- Restyle trainer create page form components to match updated tokens.
- Restyle trainer details page cards and hierarchy.
- Ensure navigation and button actions remain unchanged.

### Module 4 - Client Management
- Restyle client list page to new visual system.
- Restyle client create page fields and validation states.
- Restyle client details page summary and section cards.
- Keep provider logic as-is; UI-only pass.

### Module 5 - Attendance
- Keep current light attendance screen as base.
- Add explicit manual, QR, and backdated entry flows in the same style language.
- Add clear action states for scan success/failure and fallback manual override.

### Module 6 - Plans
- Restyle plans list cards and CTA hierarchy.
- Restyle plan creation dialog into full-screen/native-like form.
- Add plan selection and subscription visual states.

### Module 7 - Dashboard
- Keep current redesigned dashboard as baseline.
- Fine-tune spacing/typography for small screens and foldables.
- Add visual polish for chart and leaderboard responsiveness.

### Module 8 - Profile and Settings
- Restyle profile tab to align with new palette and card surfaces.
- Restyle settings controls (switches, sliders, account actions).
- Replace deprecated switch color API usage with current Flutter API.

## Quality pass before next demo
- Run: `cd mobile; flutter analyze`
- Resolve remaining analyzer info items where practical:
  - prefer const constructors where applicable
  - remove unnecessary interpolation/spreads
  - update deprecated switch color property
- Verify no runtime navigation breaks across all tabs.

## Suggested implementation order
1. Trainers and Clients (Modules 3-4)
2. Plans and Profile (Modules 6 and 8)
3. Attendance flow completion (Module 5)
4. Dashboard polish and responsive pass (Module 7)
5. Final analyzer cleanup and smoke test
