import Link from "next/link";

export default function HomePage() {
  return (
    <main className="container">
      <section className="card hero">
        <span className="pill">India-First Gym SaaS</span>
        <h1 className="hero-title">GMMX V2</h1>
        <p className="hero-subtitle">
          Gym growth OS for India. Launch your microsite, onboard your staff, and run daily attendance from one simple dashboard.
        </p>
        <div className="action-row">
          <Link href="/signup" className="button">Start 14-Day Trial</Link>
          <Link href="/pricing" className="button-secondary">See Plans</Link>
        </div>
      </section>

      <section className="grid grid-3 metrics">
        <article className="card">
          <h3>Onboard in Minutes</h3>
          <p className="muted">Create your gym, choose a slug, and go live instantly on your subdomain.</p>
        </article>
        <article className="card">
          <h3>Multi-Tenant by Default</h3>
          <p className="muted">Each gym runs isolated by tenant ID to keep data secure and clean.</p>
        </article>
        <article className="card">
          <h3>Built for Owners</h3>
          <p className="muted">Track members, trainers, and attendance with a low-friction workflow.</p>
        </article>
      </section>
    </main>
  );
}
