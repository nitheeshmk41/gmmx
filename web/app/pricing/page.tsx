const plans = [
  { name: "Starter", price: "1499", members: 100, trainers: 10 },
  { name: "Growth", price: "2999", members: 300, trainers: 30 },
  { name: "Pro", price: "4999", members: 1000, trainers: 100 }
];

export default function PricingPage() {
  return (
    <main className="container">
      <header className="page-head">
        <span className="pill">No Hidden Costs</span>
        <h1>Simple Pricing</h1>
        <p>Transparent monthly plans for independent gyms and fast-growing studios.</p>
      </header>

      <section className="grid grid-3">
        {plans.map((plan) => (
          <article className="card" key={plan.name}>
            <h2>{plan.name}</h2>
            <p className="plan-price">Rs. {plan.price}</p>
            <p className="muted">Per month</p>
            <hr style={{ border: 0, borderTop: "1px solid var(--line)", margin: "14px 0" }} />
            <p>{plan.members} member capacity</p>
            <p>{plan.trainers} trainer capacity</p>
          </article>
        ))}
      </section>
    </main>
  );
}
