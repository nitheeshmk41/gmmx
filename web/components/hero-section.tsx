import Link from "next/link";

export function HeroSection() {
  return (
    <section className="relative overflow-hidden rounded-3xl border border-rose-200/70 bg-white/80 p-8 shadow-2xl shadow-rose-100/40 animate-fade-up dark:border-slate-700 dark:bg-slate-900/70 lg:p-12">
      <div className="pointer-events-none absolute -right-20 -top-24 h-64 w-64 rounded-full bg-rose-300/40 blur-3xl dark:bg-rose-500/20" />
      <div className="pointer-events-none absolute -bottom-24 left-10 h-56 w-56 rounded-full bg-orange-200/50 blur-3xl dark:bg-orange-500/10" />

      <p className="inline-flex rounded-full border border-rose-300/60 bg-rose-50 px-4 py-1 text-sm font-semibold text-rose-600 dark:border-rose-400/30 dark:bg-rose-950/40 dark:text-rose-300">
        India-First Gym SaaS
      </p>
      <h1 className="mt-5 max-w-4xl text-4xl font-black tracking-tight text-slate-900 dark:text-slate-100 md:text-6xl">
        Gym Growth Operating System for ambitious owners.
      </h1>
      <p className="mt-4 max-w-2xl text-base text-slate-600 dark:text-slate-300 md:text-lg">
        Run attendance, automate reminders, convert leads, and launch your white-label website on
        one platform built for Indian gyms.
      </p>

      <div className="mt-8 flex flex-wrap gap-3">
        <Link href="/signup" className="btn-primary">Start 14-Day Free Trial</Link>
        <Link href="/pricing" className="btn-outline">View Pricing</Link>
        <Link href="#website" className="btn-outline">Get Gym Website</Link>
        <Link href="#contact" className="btn-outline">Contact Sales</Link>
      </div>

      <div className="mt-8 grid gap-3 sm:grid-cols-3">
        <div className="metric-card">
          <p className="metric-label">Daily check-ins</p>
          <p className="metric-value">2.4K+</p>
        </div>
        <div className="metric-card">
          <p className="metric-label">Fee reminders sent</p>
          <p className="metric-value">98K / mo</p>
        </div>
        <div className="metric-card">
          <p className="metric-label">Lead response time</p>
          <p className="metric-value">&lt; 3 mins</p>
        </div>
      </div>
    </section>
  );
}
