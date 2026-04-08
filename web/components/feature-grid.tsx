import Image from "next/image";

const features = [
  {
    title: "White-label microsites",
    description: "Launch branded pages like coachmohan.gmmx.app with lead forms and direct WhatsApp conversion.",
    icon: "🌐"
  },
  {
    title: "QR attendance in seconds",
    description: "Generate secure daily QR codes and simplify check-ins for members, trainers, and front desk teams.",
    icon: "📲"
  },
  {
    title: "WhatsApp fee reminders",
    description: "Automate reminder nudges that recover dues on time without staff manually chasing every member.",
    icon: "💸"
  },
  {
    title: "Lead CRM for follow-ups",
    description: "Track walk-ins, trial leads, and conversion stages with owner-level visibility and performance insights.",
    icon: "🎯"
  },
  {
    title: "Trainer workflows",
    description: "Daily checklists, assigned members, and progress logs to keep coaching quality consistent.",
    icon: "🏋️"
  },
  {
    title: "Owner dashboards",
    description: "Understand revenue trends, attendance consistency, and branch-level health from one dashboard.",
    icon: "📊"
  }
];

export function FeatureGrid() {
  return (
    <section id="features" className="mt-20 scroll-mt-24">
      <div className="mb-8 flex items-end justify-between gap-6">
        <div>
          <p className="section-kicker">Product Features</p>
          <h2 className="section-title">Everything your gym needs to grow reliably</h2>
        </div>
      </div>
      <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-3">
        {features.map((feature, index) => (
          <article
            key={feature.title}
            className="feature-card"
            style={{ animationDelay: `${index * 80}ms` }}
          >
            <span className="text-2xl">{feature.icon}</span>
            <h3 className="mt-4 text-lg font-bold text-slate-900 dark:text-slate-100">{feature.title}</h3>
            <p className="mt-2 text-sm leading-relaxed text-slate-600 dark:text-slate-300">{feature.description}</p>
          </article>
        ))}
      </div>

      <div className="mt-10 grid items-center gap-6 rounded-3xl border border-rose-200/70 bg-white/80 p-6 dark:border-slate-700 dark:bg-slate-900/70 lg:grid-cols-2">
        <div>
          <p className="section-kicker">Gym Website Preview</p>
          <h3 className="mt-2 text-2xl font-black text-slate-900 dark:text-white">Microsite showcase: coachmohan.gmmx.app</h3>
          <p className="mt-3 text-slate-600 dark:text-slate-300">
            Showcase trainers, plans, facilities, testimonials, and location details in a conversion-focused layout made for local leads.
          </p>
        </div>
        <div className="overflow-hidden rounded-2xl border border-slate-200 bg-slate-950 p-3 shadow-xl dark:border-slate-700">
          <Image
            src="/microsite-preview.svg"
            width={960}
            height={540}
            alt="GMMX white-label microsite preview"
            className="h-auto w-full rounded-xl"
            priority={false}
          />
        </div>
      </div>
    </section>
  );
}
