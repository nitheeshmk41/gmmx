import Link from "next/link";
import { Footer } from "@/components/footer";
import { PricingCard } from "@/components/pricing-card";
import { WebsiteUpsellCard } from "@/components/website-upsell-card";
import { saasPlans } from "@/lib/plans";
import { getWhatsAppLink, siteConfig, siteUrl } from "@/lib/site";

export const metadata = {
  title: "Pricing"
};

const pricingSchema = {
  "@context": "https://schema.org",
  "@type": "Product",
  name: "GMMX Gym SaaS Plans",
  description: "Monthly plans for gym operations, CRM, attendance and white-label websites.",
  brand: {
    "@type": "Brand",
    name: "GMMX"
  },
  offers: saasPlans.map((plan) => ({
    "@type": "Offer",
    priceCurrency: "INR",
    price: String(plan.amountInPaise / 100),
    category: plan.type,
    availability: "https://schema.org/InStock",
    url: `${siteUrl}/pricing`
  }))
};

export default function PricingPage() {
  return (
    <main>
      <div className="mx-auto w-full max-w-7xl px-4 py-10 sm:px-6 lg:px-8">
        <header className="mx-auto max-w-3xl text-center">
          <p className="section-kicker">Pricing</p>
          <h1 className="mt-2 text-4xl font-black text-slate-900 dark:text-white md:text-5xl">Transparent plans, built for every gym stage</h1>
          <p className="mt-4 text-slate-600 dark:text-slate-300">
            Start with the plan that fits your operations today and scale as your gym grows.
          </p>
        </header>

        <section className="mt-10 grid gap-5 lg:grid-cols-3">
          {saasPlans.map((plan) => (
            <PricingCard key={plan.id} plan={plan} />
          ))}
        </section>

        <WebsiteUpsellCard />

        <section className="mt-12 rounded-2xl border border-slate-200 bg-white/80 p-6 dark:border-slate-700 dark:bg-slate-900/70">
          <h2 className="text-2xl font-black text-slate-900 dark:text-white">Need custom onboarding support?</h2>
          <p className="mt-2 text-slate-600 dark:text-slate-300">
            Talk to our team for migration help, multi-branch setup, and operations workflow design.
          </p>
          <div className="mt-5 flex flex-wrap gap-3">
            <a href={getWhatsAppLink("Hi GMMX, I need help selecting a plan.")} target="_blank" rel="noreferrer" className="btn-primary">
              WhatsApp us
            </a>
            <a href={`tel:${siteConfig.phone}`} className="btn-outline">Call sales</a>
            <Link href="/signup" className="btn-outline">Book demo</Link>
          </div>
        </section>

        <Footer />
      </div>

      <script
        type="application/ld+json"
        dangerouslySetInnerHTML={{ __html: JSON.stringify(pricingSchema) }}
      />
    </main>
  );
}
