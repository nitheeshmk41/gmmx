import Link from "next/link";

export default function HomePage() {
  return (
    <main className="container">
      <h1>GMMX V2</h1>
      <p>Gym growth OS for India. Launch your microsite and dashboard in one flow.</p>
      <div style={{ display: "flex", gap: 12, marginTop: 16 }}>
        <Link href="/signup" className="button">Start 14-Day Trial</Link>
        <Link href="/pricing" className="card">See Plans</Link>
      </div>
    </main>
  );
}
