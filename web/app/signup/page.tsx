"use client";

import { FormEvent, useState } from "react";

type RegisterResponse = {
  slug: string;
  trialEndsAt: string;
};

export default function SignupPage() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [result, setResult] = useState<RegisterResponse | null>(null);

  async function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setLoading(true);
    setError("");

    const form = new FormData(event.currentTarget);
    const payload = {
      ownerName: String(form.get("ownerName") ?? ""),
      mobile: String(form.get("mobile") ?? ""),
      email: String(form.get("email") ?? ""),
      gymName: String(form.get("gymName") ?? ""),
      location: String(form.get("location") ?? ""),
      slug: String(form.get("slug") ?? ""),
      password: String(form.get("password") ?? "")
    };

    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/api/auth/owner/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });

      const data = await response.json();
      if (!response.ok) {
        throw new Error(data.error ?? "Signup failed");
      }

      setResult({ slug: data.slug, trialEndsAt: data.trialEndsAt });
    } catch (e) {
      setError(e instanceof Error ? e.message : "Signup failed");
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="container">
      <h1>Owner Onboarding</h1>
      <p>Create your gym and start the 14-day free trial.</p>

      <form className="card" onSubmit={onSubmit} style={{ maxWidth: 620 }}>
        <label>Owner Name<input className="input" name="ownerName" required /></label>
        <label>Mobile (10 digits)<input className="input" name="mobile" pattern="[0-9]{10}" required /></label>
        <label>Email<input className="input" name="email" type="email" required /></label>
        <label>Gym Name<input className="input" name="gymName" required /></label>
        <label>Location<input className="input" name="location" required /></label>
        <label>Gym Slug<input className="input" name="slug" pattern="[a-z0-9-]{3,30}" required /></label>
        <label>Password<input className="input" name="password" type="password" minLength={6} required /></label>
        <button className="button" type="submit" disabled={loading} style={{ marginTop: 16 }}>
          {loading ? "Creating..." : "Create Gym"}
        </button>
      </form>

      {error && <p style={{ color: "crimson" }}>{error}</p>}
      {result && (
        <section className="card" style={{ marginTop: 16 }}>
          <h2>Success</h2>
          <p>Tenant created: {result.slug}.gmmx.app</p>
          <p>Trial ends at: {new Date(result.trialEndsAt).toLocaleString()}</p>
          <a className="button" href={`/${result.slug}/dashboard`}>Go to Dashboard</a>
        </section>
      )}
    </main>
  );
}
