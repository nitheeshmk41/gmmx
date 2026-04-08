import Image from "next/image";
import Link from "next/link";
import { FAQAccordion } from "@/components/faq-accordion";
import { FeatureGrid } from "@/components/feature-grid";
import { Footer } from "@/components/footer";
import { HeroSection } from "@/components/hero-section";
import { TestimonialSection } from "@/components/testimonial-section";
import { WebsiteUpsellCard } from "@/components/website-upsell-card";
import { getWhatsAppLink, siteConfig, siteUrl } from "@/lib/site";

const businessSchema = {
  "@context": "https://schema.org",
  "@type": "SoftwareApplication",
  name: "GMMX",
  applicationCategory: "BusinessApplication",
  operatingSystem: "Web, iOS, Android",
  offers: {
    "@type": "AggregateOffer",
    lowPrice: "499",
    highPrice: "1499",
    priceCurrency: "INR"
  },
  description: siteConfig.description,
  url: siteUrl,
  provider: {
    "@type": "Organization",
    name: "GMMX",
    telephone: siteConfig.phone
  }
};

export default function HomePage() {
  return (
    <main>
      <div className="mx-auto w-full max-w-7xl px-4 py-8 sm:px-6 lg:px-8">
        <HeroSection />
        <FeatureGrid />

        <section className="mt-16 grid gap-5 md:grid-cols-3">
          <article className="rounded-2xl border border-slate-200 bg-white/80 p-6 dark:border-slate-700 dark:bg-slate-900/70">
            <h3 className="text-lg font-bold text-slate-900 dark:text-white">QR Attendance</h3>
            <p className="mt-2 text-sm text-slate-600 dark:text-slate-300">Member check-ins from a daily secure QR flow with same-day duplication controls.</p>
          </article>
          <article className="rounded-2xl border border-slate-200 bg-white/80 p-6 dark:border-slate-700 dark:bg-slate-900/70">
            <h3 className="text-lg font-bold text-slate-900 dark:text-white">WhatsApp Fee Reminders</h3>
            <p className="mt-2 text-sm text-slate-600 dark:text-slate-300">Automated fee follow-ups designed for India-first gym communication behavior.</p>
          </article>
          <article className="rounded-2xl border border-slate-200 bg-white/80 p-6 dark:border-slate-700 dark:bg-slate-900/70">
            <h3 className="text-lg font-bold text-slate-900 dark:text-white">Lead CRM</h3>
            <p className="mt-2 text-sm text-slate-600 dark:text-slate-300">Capture walk-ins, assign follow-ups, and close memberships with structured workflows.</p>
          </article>
        </section>

        <section className="mt-16 grid items-center gap-8 rounded-3xl border border-slate-200 bg-white/80 p-8 dark:border-slate-700 dark:bg-slate-900/70 lg:grid-cols-2">
          <div>
            <p className="section-kicker">Mobile App Preview</p>
            <h2 className="mt-2 text-3xl font-black text-slate-900 dark:text-white">Owner + trainer + member experience in your pocket</h2>
            <p className="mt-3 text-slate-600 dark:text-slate-300">
              Real-time attendance, dashboard visibility, reminders, and member progression tracking with role-based access.
            </p>
            <div className="mt-6 flex flex-wrap gap-3">
              <Link href="/pricing" className="btn-primary">View Pricing</Link>
              <a href={getWhatsAppLink("Hi GMMX team, I need a mobile app demo.")} target="_blank" rel="noreferrer" className="btn-outline">
                Book demo
              </a>
            </div>
          </div>
          <div className="rounded-2xl border border-slate-200 bg-slate-100 p-3 dark:border-slate-700 dark:bg-slate-950">
            <Image
              src="/mobile-app-preview.svg"
              width={640}
              height={420}
              alt="GMMX mobile app preview"
              className="h-auto w-full rounded-xl"
              loading="lazy"
            />
          </div>
        </section>

        <WebsiteUpsellCard />
        <TestimonialSection />
        <FAQAccordion />
        <Footer />
      </div>

      <script
        type="application/ld+json"
        dangerouslySetInnerHTML={{ __html: JSON.stringify(businessSchema) }}
      />
    </main>
  );
}
