import React, {useEffect, useState} from 'react';
import { useParams } from 'react-router-dom';
import api from '../services/api';

export default function JobDetail(){
  const { id } = useParams();
  const [job,setJob] = useState(null);
  useEffect(()=>{ api.get('/jobs/'+id).then(r=>setJob(r.data)).catch(e=>console.error(e)) },[id]);
  if(!job) return <div>Loading...</div>
  return (
    <div>
      <h3>{job.title}</h3>
      <p><strong>Company:</strong> {job.company}</p>
      <p><strong>Location:</strong> {job.location}</p>
      <p>{job.description}</p>
    </div>
  )
}
