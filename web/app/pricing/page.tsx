const plans = [
  { name: "Starter", price: "1499", members: 100, trainers: 10 },
  { name: "Growth", price: "2999", members: 300, trainers: 30 },
  { name: "Pro", price: "4999", members: 1000, trainers: 100 }
];

export default function PricingPage() {
  return (
    <main className="container">
      <h1>Pricing</h1>
      <p>Simple plans for Indian gyms, billed monthly.</p>
      <section className="grid grid-3" style={{ marginTop: 16 }}>
        {plans.map((plan) => (
          <article className="card" key={plan.name}>
            <h2>{plan.name}</h2>
            <p style={{ fontSize: 28, margin: "8px 0" }}>Rs. {plan.price}</p>
            <p>{plan.members} members</p>
            <p>{plan.trainers} trainers</p>
          </article>
        ))}
      </section>
    </main>
  );
}
