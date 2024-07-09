import React from 'react';
import './style.css';

const ContactInfo = () => {
  return (
    <aside className="contact-info">
      <h2 className="contact-title">Contact Info</h2>
      <address className="address">
        Address: <br />
        <span>608 5th Avenue</span> <br />
        <span>Suite 507</span> <br />
        <span>New York, NY 10020</span>
      </address>
      <p className="hours">
        Hours: <br />
        <span>Mon-Fri 10am-6pm EST</span> <br />
        <span>Office Visits by Appointment Only</span>
      </p>
      <p className="phone">
        Phone: <br />
        <span>(212) 389-9040</span>
      </p>
      <p className="whatsapp">
        WhatsApp: <br />
        <span>+1 212-389-9040</span>
      </p>
      <p className="email">
        Email: <br />
        <span>info@MercuryAuction.com</span>
      </p>
      <p className="languages">
        Languages: <br />
        <span>English</span> <br />
        <span>French</span> <br />
        <span>Spanish</span> <br />
        <span>Italian</span> <br />
        <span>Russian</span> <br />
        <span>Croatian</span> <br />
        <span>Mandarin</span> <br />
        <span>Cantonese</span>
      </p>
    </aside>
  );
};

export default ContactInfo;