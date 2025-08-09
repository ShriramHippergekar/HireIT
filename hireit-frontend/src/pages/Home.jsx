import React, {useEffect, useState} from 'react';
import api from '../services/api';
import { Link } from 'react-router-dom';

export default function Home(){
  const [jobs,setJobs] = useState([]);
  useEffect(()=>{ api.get('/jobs').then(r=>setJobs(r.data)).catch(e=>console.error(e)) },[]);
  return (
    <div>
      <h3>Jobs</h3>
      <ul>
        {jobs.map(j => <li key={j.id}><Link to={'/jobs/'+j.id}>{j.title}</Link> — {j.company}</li>)}
      </ul>
    </div>
  )
}
